<template>
  <div class="my-20">
    <nav
      class="bg-gray-900 text-white z-50 fixed top-0 border-b w-full md:text-sm md:border-none"
    >
      <div class="items-center px-4 max-w-screen-xl mx-auto md:flex md:px-8">
        <div class="flex items-center justify-between md:block">
          <NuxtLink to="/">
            <h1 class="text-2xl font-bold text-white py-3">Guazy Blog.</h1>
          </NuxtLink>
          <div class="md:hidden">
            <button
              class="text-gray-500 hover:text-gray-800"
              @click="toggleState"
            >
              <template v-if="state">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-6 w-6"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                >
                  <path
                    fill-rule="evenodd"
                    d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                    clip-rule="evenodd"
                  />
                </svg>
              </template>
              <template v-else>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="currentColor"
                  class="w-6 h-6"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"
                  />
                </svg>
              </template>
            </button>
          </div>
        </div>
        <div
          class="flex-1 pb-3 mt-8 md:block md:pb-0 md:mt-0"
          :class="{ block: state, hidden: !state }"
        >
          <ul
            class="justify-end items-center space-y-6 md:flex md:space-x-6 md:space-y-0"
          >
            <div v-for="(item, idx) in navigation" :key="idx">
              <li class="text-white hover:text-indigo-600">
                <NuxtLink :to="item.path" class="block">
                  {{ item.title }}
                </NuxtLink>
              </li>
            </div>
            <span class="hidden w-px h-6 bg-gray-300 md:block"></span>
            <div
              class="space-y-3 items-center gap-x-6 md:flex md:space-y-0"
              v-if="!isLoggedIn"
            >
              <li>
                <nuxt-link
                  to="/login"
                  href="javascript:void(0)"
                  class="block rounded py-1 px-4 font-medium text-center text-white bg-indigo-600 hover:bg-indigo-500 active:bg-indigo-700 active:shadow-none shadow md:inline"
                >
                  Log in
                </nuxt-link>
              </li>
              <li>
                <nuxt-link
                  to="/register"
                  class="block py-1 text-center text-white hover:text-indigo-600 border rounded-lg md:border-none"
                >
                  Register
                </nuxt-link>
              </li>
            </div>
            <div
              class="space-y-3 items-center gap-x-6 md:flex md:space-y-0"
              v-else
            >
              <li>
                <nuxt-link
                  to="/admin/blogs"
                  href="javascript:void(0)"
                  class="block rounded py-1 px-4 font-medium text-center text-white bg-indigo-700 hover:bg-indigo-500 active:bg-indigo-700 active:shadow-none shadow md:inline"
                >
                  Admin
                </nuxt-link>
              </li>
              <li>
                <a
                  href="/"
                  @click="logout"
                  class="block py-1 text-center text-white hover:text-indigo-600 border rounded-lg md:border-none"
                >
                  Log out
                </a>
              </li>
            </div>
          </ul>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  setup() {
    const state = ref(false);
    // const isLoggedIn = ref(false);
    // Computed property to check if user is logged in
    const isLoggedIn = computed(() => {
      const userInfo = localStorage.getItem("user_details");
      const parsedUserInfo = userInfo ? JSON.parse(userInfo) : null;

      if (parsedUserInfo == null || !parsedUserInfo.token) {
        return false;
      } else {
        return true;
      }
    });

    const toggleState = () => {
      state.value = !state.value;
    };
    const logout = () => {
      localStorage.clear();
      // window.location.href("/");
    };

    const navigation = [
      { title: "Home", path: "/" },
      { title: "Blogs", path: "/blogs" },
      { title: "Contact", path: "/contact" },
    ];

    return {
      state,
      toggleState,
      logout,
      navigation,
      isLoggedIn,
    };
  },
};
</script>

<style scoped>
/* Add your Tailwind CSS styles here */
</style>
