<template>
  <div class="-mt-10">
    <div class="bg-gray-50">
      <div class="text-center mt-12 p-10">
        <h1>
          <span class="font-bold">{{ "All Blogs" }}</span>
          <span class="mx-4">|</span
          ><span
            ><nuxt-link to="/" class="text-indigo-600">Home</nuxt-link></span
          >
        </h1>
      </div>
    </div>
    <div class="my-5 px-5 md:px-10">
      <page-blog />
    </div>
  </div>
</template>

<script>
import PageBlog from "../../components/page/blog.vue";
export default {
  components: { PageBlog },
};
</script>

<style lang="sass" scoped></style>
