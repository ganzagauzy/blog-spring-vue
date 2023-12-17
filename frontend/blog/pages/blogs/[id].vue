<template>
  <div class="-mt-10">
    <div class="bg-gray-50">
      <div class="text-center mt-12 p-10">
        <h1>
          <span class="font-bold">{{ blog.title }}</span>
          <span class="mx-4">|</span
          ><span
            ><nuxt-link to="/" class="text-indigo-600">Home</nuxt-link></span
          >
        </h1>
      </div>
    </div>
    <div class="my-10 px-4 md:px-8">
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
                <div v-for="(comment, i) in comments" :key="`comment` + i">
                  <div class="bg-white mb-4 p-2 rounded-lg">
                    <h2 class="font-bold mb-3">{{ comment.name }}</h2>
                    <p>{{ comment.content }}</p>
                  </div>
                </div>
              </div>
              <div v-else>
                <p>No comments for {{ blog.title }}</p>
              </div>
              <div class="my-5 bg-white p-5 rounded-lg">
                <form class="" @submit.prevent="submitComment">
                  <div class="mb-5">
                    <label
                      for="name"
                      class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                      >Your name</label
                    >
                    <input
                      type="text"
                      id="name"
                      class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-indigo-500 dark:focus:border-indigo-500"
                      placeholder="Name Name"
                      required
                      v-model="name"
                    />
                  </div>
                  <div class="mb-5">
                    <label
                      for="email"
                      class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                      >Your email</label
                    >
                    <input
                      type="email"
                      id="email"
                      class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-indigo-500 focus:border-indigo-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-indigo-500 dark:focus:border-indigo-500"
                      placeholder="name@flowbite.com"
                      required
                      v-model="email"
                    />
                  </div>
                  <div class="mb-5">
                    <label
                      for="message"
                      class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                      >Your comment</label
                    >
                    <textarea
                      id="message"
                      rows="3"
                      class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                      placeholder="Write your thoughts here..."
                      required
                      v-model="message"
                    ></textarea>
                  </div>

                  <button
                    type="submit"
                    class="text-white bg-indigo-600 hover:bg-indigo-800 focus:ring-4 focus:outline-none focus:ring-indigo-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-indigo-600 dark:hover:bg-indigo-700 dark:focus:ring-indigo-800"
                    :class="{
                      'cursor-not-allowed opacity-50': isComment,
                    }"
                    :disabled="isComment"
                  >
                    {{ isComment ? "Loading..." : "Submit Comment" }}
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { useAxios } from "../../composables/useAxios";

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
      isComment: false,
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
      name: "",
      email: "",
      message: "",
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
    async submitComment() {
      try {
        this.isComment = true;
        this.showAlert = false;
        const { data, error, pending } = await useAxios("comments", {
          method: "POST",
          body: {
            content: this.message,
            post: this.blog,
            name: this.name,
            email: this.email,
          },
        });

        if (data.value) {
          console.log("data", data.value);
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
        this.isComment = false;
        this.name = "";
        this.email = "";
        this.message = "";
        this.getBlog();
        this.getComments();
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
