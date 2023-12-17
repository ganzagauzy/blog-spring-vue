<template>
  <div class="-mt-10">
    <div class="bg-gray-50">
      <div class="text-center mt-12 p-10">
        <h1>
          <span class="font-bold">{{ "Manage Blogs" }}</span>
          <span class="mx-4">|</span
          ><span
            ><nuxt-link to="/admin/blogs" class="text-indigo-600"
              >Admin</nuxt-link
            ></span
          >
        </h1>
      </div>
    </div>
    <div>
      <admin-blogs />
    </div>
  </div>
</template>

<script>
import AdminBlogs from "../../../components/admin/blogs.vue";
definePageMeta({
  middleware: "admin-guard",
});
export default {
  data() {
    return {};
  },
  components: { AdminBlogs },
};
</script>

<style lang="sass" scoped></style>
