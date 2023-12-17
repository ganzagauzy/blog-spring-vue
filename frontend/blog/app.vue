<template>
  <Html :class="{ dark: theme }">
    <Body
      class="antialiased duration-300 transition-colors text-gray-800 dark:text-gray-200 bg-white dark:bg-gray-900 overscroll-y-none"
    >
      <NuxtLayout>
        <NuxtPage />
      </NuxtLayout>
    </Body>
  </Html>
</template>

<script setup>
import { onMounted } from "vue";
import { initFlowbite } from "flowbite";
import { useTheme } from "./composables/state";

useSeoMeta({
  charset: "UTF-8",
  robots: "index,follow",
  contentType: "text/html",
  viewport: "width=device-width, initial-scale=1",
  ogLocale: "en_US",
  ogLocaleAlternate: "en_US",
  title: "Guazy Blog.",
  titleTemplate: (titleChunk) => {
    return titleChunk ? `${titleChunk} - Guazy Blog.` : "Guazy Blog.";
  },
  description: "",
  image: "https://via.placeholder.com/150",
  creator: "@intekoy'umuco",
  author: "Guazy Blog.",
  applicationName: "Guazy Blog.",
  ogSiteName: "Guazy Blog.",
  ogTitle: "Guazy Blog.",
  ogDescription: "Guazy Blog. ",
  keywords: "inteko, umuco",
});

const theme = useTheme();

onMounted(() => {
  initFlowbite();
});
</script>
