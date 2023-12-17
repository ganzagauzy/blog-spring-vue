<template>
  <main
    class="w-full h-screen flex flex-col items-center justify-center bg-gray-50 sm:px-4"
  >
    <div class="w-full space-y-6 text-gray-600 sm:max-w-md">
      <div class="text-center">
        <nuxt-link to="/">
          <!-- <img src="https://floatui.com/logo.svg" width="150" class="mx-auto" /> -->
          <h2 class="text-3xl font-bold text-indigo-600">Guazy Blog.</h2>
        </nuxt-link>
        <div class="mt-5 space-y-2">
          <h3 class="text-gray-800 text-2xl font-bold sm:text-3xl">
            Log in to account
          </h3>
          <p class="">
            Don't have an account?
            <nuxt-link
              to="/register"
              class="font-medium text-indigo-600 hover:text-indigo-500"
              >Register</nuxt-link
            >
          </p>
        </div>
      </div>
      <div class="bg-white shadow p-4 py-6 sm:p-6 sm:rounded-lg">
        <form class="space-y-5" @submit.prevent="login">
          <div>
            <label class="font-medium">Email</label>
            <input
              type="email"
              v-model="email"
              required
              class="w-full mt-2 px-3 py-2 text-gray-500 bg-transparent outline-none border focus:border-indigo-600 shadow-sm rounded-lg"
            />
          </div>
          <div>
            <label class="font-medium">Password</label>
            <input
              type="password"
              v-model="password"
              required
              class="w-full mt-2 px-3 py-2 text-gray-500 bg-transparent outline-none border focus:border-indigo-600 shadow-sm rounded-lg"
            />
          </div>
          <div v-if="showAlert" class="">
            <alert-5
              v-if="errorMsg != ''"
              type="danger"
              title="Failed"
              :text="errorMsg"
              class="mb-4"
            />
          </div>
          <button
            type="submit"
            href="dashboard"
            class="w-full text-white bg-indigo-600 hover:bg-indigo-600 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-gray-600 dark:hover:bg-gray-700 dark:focus:ring-gray-800"
            :class="{
              'cursor-not-allowed opacity-50': isLoading,
            }"
            :disabled="isLoading"
          >
            {{ isLoading ? "Loading..." : "Login" }}
          </button>
        </form>
      </div>
    </div>
  </main>
</template>

<script lang="ts">
import { useAxios } from "../composables/useAxios";
definePageMeta({
  layout: "empty",
  // middleware: "admin-guard",
});
export default {
  data() {
    return {
      checkbox: false,
      show1: false,
      showAlert: false,
      isRegistry: false,
      errorMsg: "",
      password: "",
      email: "",
      username: "",
      errors: "",
      showCp: false,
      showP: false,
      bodyBackgroundColorClass: "bg-white",
      isLoading: false,
    };
  },
  computed: {
    cardClasses() {
      const screenWidth = window.innerWidth;

      let widthClass = "";

      if (screenWidth < 640) {
        widthClass = "w-300";
      } else if (screenWidth >= 768 && screenWidth < 1024) {
        widthClass = "w-400";
      }

      return "p-4 rounded shadow " + widthClass;
    },
  },
  mounted() {
    // this.setCardBackgroundColor()
  },

  methods: {
    async login() {
      try {
        this.isLoading = true;
        this.showAlert = false;
        const { data, error, pending } = await useAxios("users/login", {
          method: "POST",
          body: {
            email: this.email,
            password: this.password,
          },
        });

        if (data.value) {
          // console.log("data", data.value);
          localStorage.setItem("user_details", JSON.stringify(data.value));
          if (data.value.user.role === "ADMIN") {
            this.$router.push(`/admin`);
          } else {
            this.$router.push(`/admin/blogs`);
          }
        } else {
          this.showAlert = true;
          console.log("error", error.value?.data.errors);
          this.errorMsg = error.value?.data.errors || "Error Occured!";
        }
      } catch (error) {
        this.showAlert = true;
        console.log("error", error);
        this.errorMsg = "Error Occured!";
      } finally {
        this.isLoading = false;
      }
    },

    clearInputs() {
      this.username = "";
      this.email = "";
      this.password = "";
    },
  },
};
</script>

<style lang="scss" scoped>
//   @import '~vuetify/dist/vuetify.min.css';

//   $primary-color: #1E555C;

body {
  margin: 0 !important;
}

.brand-logo {
  background: #06283d;
  padding: 5px 10px;
  border-radius: 2px;
  border-top-right-radius: 15px;
}

.bg-primary-color {
  // background-color: #163a46;
  height: 60vh;
  clip-path: polygon(100% 0, 100% 75%, 50% 100%, 0 80%, 0 0);
}

.form-container {
  position: absolute;
  width: 400px;
  //   background: #101724 !important;
  top: 150px;
  left: 50%;
  transform: translateX(-50%);
  @media (max-width: 639px) {
    width: 300px;
  }
}

.bg-darker {
  background-color: #ffffff;
}
.dark .bg-darker {
  background-color: #1f2937 !important;
}
input[type="checkbox"]:checked {
  background-color: red !important; /* Red background color when checked */
}

.img-container img {
  height: 500px;
  margin: 0 auto;
  @media screen and (max-width: 980px) {
    width: 200px;
  }
}
</style>
