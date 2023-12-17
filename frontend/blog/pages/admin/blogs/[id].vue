<template>
  <div class="-mt-10">
    <div class="bg-gray-50">
      <div class="text-center mt-12 p-10">
        <h1>
          <span class="font-bold">{{ blog.title }}</span>
          <span class="mx-4">|</span
          ><span
            ><nuxt-link to="/admin/blogs" class="text-indigo-600"
              >Admin</nuxt-link
            ></span
          >
        </h1>
      </div>
    </div>
    <div class="my-5 px-4 md:px-8">
      <div class="flex flex-col md:flex-row">
        <div class="md:w-2/5">
          <div class="grid gap-4">
            <div v-for="(img, i) in blog.images" :key="i">
              <img class="h-auto max-w-full rounded-lg" :src="img" alt="" />
            </div>
          </div>
        </div>
        <div class="md:mx-5 my-5 md:my-0"></div>
        <div class="bg-gray-50 p-4 rounded-lg flex-grow">
          <h2 class="font-bold text-2xl">{{ blog.title }}</h2>
          <p
            class="my-5 text-gray-600 text-sm duration-150 group-hover:text-gray-800"
          >
            {{ blog.content }}
          </p>
          <div>
            <h2 class="font-bold text-2xl">{{ "Comments" }}</h2>
            <div class="my-5">
              <div v-if="comments.length > 0">
                <div v-for="(comment, i) in comments" :key="i">
                  <div class="bg-white mb-4 p-2 rounded-lg">
                    <h3 class="font-bold mb-3">{{ comment.name }}</h3>
                    <p>{{ comment.content }}</p>
                  </div>
                </div>
              </div>
              <div v-else>
                <p>No comments for {{ blog.title }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { useAxios } from "../../../composables/useAxios";
definePageMeta({
  middleware: "admin-guard",
});
export default {
  data() {
    return {
      isPaid: false,
      headerActions: false,
      headerFilter: false,
      comments: [] as any,
      user: {} as any,
      placeById: {} as any,
      blog: {} as any,
      isLoading: false,
      isPaying: false,
      showAlert: false,
      ascend: false,
      descend: false,
      filterBy: "asc",
      errorMsg: "",
      from: 0,
      to: 0,
      total: 0,
      per_page: 0,
      page: 1,
      last_page: 1,
      email: "",
    };
  },
  computed: {
    id() {
      return this.$route.params.id;
    },
  },
  created() {
    this.getBlog();
    this.getComments();
  },
  methods: {
    async getBlog() {
      this.blog = {};
      try {
        this.isLoading = true;
        const { data, error, pending } = await useAxios(`posts/${this.id}`, {
          method: "GET",
        });
        if (data.value) {
          console.log("blog", data.value);
          this.blog = data.value;
        }
        if (error.value) {
          this.showAlert = true;
          this.errorMsg = error.value.data.errors;
        }
      } catch (error) {
        // console.log("error", error);
      } finally {
        this.isLoading = false;
      }
    },
    async getComments() {
      this.blog = {};
      try {
        this.isLoading = true;
        const { data, error, pending } = await useAxios(
          `comments/post/${this.id}`,
          {
            method: "GET",
          }
        );
        if (data.value) {
          console.log("blog", data.value);
          this.comments = data.value;
          console.log("comments", data.value);
        }
        if (error.value) {
          this.showAlert = true;
          this.errorMsg = error.value.data.errors;
        }
      } catch (error) {
        // console.log("error", error);
      } finally {
        this.isLoading = false;
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
