<template>
  <div>
    <PageNavbar />
    <slot />
    <footer class="pt-10 bg-gray-900">
      <div class="max-w-screen-xl mx-auto px-4 md:px-8">
        <div class="flex-1 space-y-6 justify-between sm:flex md:space-y-0">
          <ul
            v-for="(item, idx) in footerNavs"
            :key="idx"
            class="space-y-4 text-gray-300"
          >
            <h4 class="text-gray-200 font-semibold sm:pb-2">
              {{ item.label }}
            </h4>
            <li v-for="(el, index) in item.items" :key="index" class="text-sm">
              <nuxt-link :to="el.href" class="duration-150 hover:text-gray-400"
                >{{ el.name }}
              </nuxt-link>
            </li>
          </ul>
        </div>
        <div class="py-2 mt-4 border-t border-gray-700 text-sm">
          <p class="text-center text-gray-300">
            © 2023 Guazy Blog. All rights reserved.
          </p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
import { useTheme } from "../composables/state";
import { onMounted, defineComponent } from "vue";
import { initFlowbite } from "flowbite";
onMounted(() => {
  initFlowbite();
});

export default {
  name: "DefaultLayout",
  setup() {
    const theme = useTheme();

    const toggleTheme = () => {
      theme.value = !theme.value;
    };
    const footerNavs = [
      {
        label: "Guazy Blog.",
        items: [
          {
            href: "javascript:void()",
            name: "Blogs from guazy",
          },
        ],
      },
      {
        label: "Quick Links",
        items: [
          {
            href: "/",
            name: "Home",
          },
          {
            href: "/blogs",
            name: "Blogs",
          },
          {
            href: "/contact",
            name: "Contact",
          },
        ],
      },
      {
        label: "Team",
        items: [
          {
            href: "",
            name: "Ganza Ntambuko Sanchess",
          },
        ],
      },
    ];

    return {
      theme,
      toggleTheme,
      footerNavs,
    };
  },
};
</script>

<style scoped></style>
