<template>
  <div>
    <div v-if="showAlert" class="">
      <div class="flex justify-end">
        <svg
          @click="showAlert = false"
          class="w-6 h-6 text-red dark:text-white cursor-pointer"
          aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg"
          fill="red"
          viewBox="0 0 20 20"
        >
          <path
            d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 11.793a1 1 0 1 1-1.414 1.414L10 11.414l-2.293 2.293a1 1 0 0 1-1.414-1.414L8.586 10 6.293 7.707a1 1 0 0 1 1.414-1.414L10 8.586l2.293-2.293a1 1 0 0 1 1.414 1.414L11.414 10l2.293 2.293Z"
          />
        </svg>
      </div>
      <alert-5
        v-if="errorMsg != ''"
        type="danger"
        title="Failed"
        :text="errorMsg"
        class="mb-4"
      />
    </div>
    <div class="md:mt-5">
      <button
        class="bg-indigo-600 text-white text-sm mt-10 md:mt-0 my-5 px-4 p-1 rounded"
        @click="getBlogs"
      >
        Refresh
      </button>
    </div>

    <!-- {{ blogs[0] }} -->
    <div v-if="isLoading" class="">Fetching data ....</div>
    <div v-else-if="blogs == null" class="">No data available ...</div>
    <div class="">
      <section class="mb-16">
        <div class="max-w-screen-xl mx-auto">
          <ul class="grid gap-x-8 gap-y-10 sm:grid-cols-2 lg:grid-cols-3">
            <li
              v-for="(blog, index) in blogs"
              :key="index"
              class="w-full mx-auto group sm:max-w-sm"
            >
              <nuxt-link :to="'blogs/' + blog.id">
                <img
                  v-if="blog.images != null"
                  :src="blog.images[0]"
                  loading="lazy"
                  :alt="blog.title"
                  class="w-full rounded-lg"
                />
                <div class="mt-3 space-y-2">
                  <div class="flex justify-between">
                    <span class="block text-indigo-600 text-sm">{{
                      blog.author.name
                    }}</span>
                    <span class="block text-indigo-600 text-sm">{{
                      blog.date
                    }}</span>
                  </div>
                  <h3
                    v-if="blog.title.lenght > 35"
                    class="text-lg text-gray-800 duration-150 group-hover:text-indigo-600 font-semibold"
                  >
                    {{ blog.title + " ..." }}
                  </h3>
                  <h3
                    v-else
                    class="text-lg text-gray-800 duration-150 group-hover:text-indigo-600 font-semibold"
                  >
                    {{ blog.title }}
                  </h3>
                  <p
                    v-if="blog.content.length > 200"
                    class="text-gray-600 text-sm duration-150 group-hover:text-gray-800"
                  >
                    {{ blog.content + ".... ..." }}
                  </p>
                  <p
                    v-else
                    class="text-gray-600 text-sm duration-150 group-hover:text-gray-800"
                  >
                    {{ blog.content }}
                  </p>
                </div>
              </nuxt-link>
            </li>
          </ul>
        </div>
      </section>
    </div>
  </div>
</template>

<script lang="ts">
import { useAxios } from "../../composables/useAxios";
export default {
  data() {
    return {
      headerActions: false,
      headerFilter: false,
      blogs: [] as any,
      user: {} as any,
      featuredPlace: {} as any,
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
      // blogs: [
      //   {
      //     title: "What is SaaS? Software as a Service Explained",
      //     desc: "Going into this journey, I had a standard therapy regimen, based on looking at the research literature. After I saw the movie, I started to ask other people.",
      //     img: "https://images.unsplash.com/photo-1556155092-490a1ba16284?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80",
      //     date: "Jan 4 2022",
      //     href: "javascript:void(0)",
      //   },
      //   {
      //     title: "A Quick Guide to WordPress Hosting",
      //     desc: "According to him, “I'm still surprised that this has happened. But we are surprised because we are so surprised.” More revelations.",
      //     img: "https://images.unsplash.com/photo-1620287341056-49a2f1ab2fdc?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80",
      //     date: "Jan 4 2022",
      //     href: "javascript:void(0)",
      //   },
      //   {
      //     title: "7 Promising VS Code Extensions Introduced in 2022",
      //     desc: "I hope I remembered all the stuff that they needed to know. They're like, 'okay,' and write it in their little reading notebooks.",
      //     img: "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80",
      //     date: "Jan 4 2022",
      //     href: "javascript:void(0)",
      //   },
      //   {
      //     title: "How to Use Root C++ Interpreter Shell to Write C++ Programs",
      //     desc: "The powerful gravity waves resulting from the impact of the planets' moons — four in total — were finally resolved in 2015 when gravitational.",
      //     img: "https://images.unsplash.com/photo-1617529497471-9218633199c0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80",
      //     date: "Jan 4 2022",
      //     href: "javascript:void(0)",
      //   },
      // ],
    };
  },
  created() {
    this.getBlogs();
  },
  methods: {
    async getBlogs() {
      this.blogs = [];
      try {
        this.isLoading = true;
        const { data, error, pending } = await useAxios(`posts`, {
          method: "GET",
        });
        if (data) {
          this.blogs = data.value;
          console.log("blogs", data);
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
    getPrevious() {
      if (this.page > 1) {
        this.page = this.page - 1;
        this.getBlogs();
      } else {
        this.page = 1;
        this.getBlogs();
      }
    },
    FilterAscend() {
      // console.log("hii", this.ascend);
      if (this.ascend) {
        this.descend = false;
        this.filterBy = "asc";
        this.getBlogs();
      }
      this.headerFilter = false;
    },
    FilterDescend() {
      // console.log("hii", this.descend);
      if (this.descend) {
        this.ascend = false;
        this.filterBy = "desc";
        this.getBlogs();
      }
      this.headerFilter = false;
    },
    getNext() {
      if (this.page < this.last_page) {
        this.page = this.page + 1;
        this.getBlogs();
      } else {
        this.getBlogs();
      }
    },
    goFirst() {
      this.page = 1;
      this.getBlogs();
    },
    goLast() {
      this.page = this.last_page;
      this.getBlogs();
    },
    goTopage(i: any) {
      this.page = i;
      this.getBlogs();
    },
    async payVideo(place: any) {
      console.log("place", place.id);
      try {
        this.showAlert = false;
        this.isPaying = true;
        const { data, error, pending } = await useAxios(
          `/infoBeforePayment/${place.id}`,
          {
            method: "GET",
          }
        );
        if (data.value) {
          this.featuredPlace = data.value.place;
          this.user = data.value.user;
          // console.log("user", this.user);
          // console.log("place", this.featuredPlace);
          this.payNow();
        }
        if (error.value) {
          this.showAlert = true;
          this.errorMsg = error.value.data.errors;
        }
      } catch (error) {
        // console.log("error", error);
      } finally {
      }
    },
    async payNow() {
      try {
        this.showAlert = false;
        console.log("paying");

        const { data, error, pending } = await useAxios("pay", {
          method: "POST",
          body: {
            // email: this.user.email,
            // first_name: this.user.first_name,
            // last_name: this.user.last_name,
            // phone_number: this.user.phone_number,
            place_id: this.featuredPlace.id,
            place_location: this.featuredPlace.place_location,
            amount: this.featuredPlace.amount,
            place_name: this.featuredPlace.place_name,
          },
        });

        if (data.value) {
          console.log("data", data.value);
          const url = data.value;
          window.location.href = url;
        } else {
          this.showAlert = true;
          // console.log("error", error.value?.data?.message);
          this.errorMsg = error.value?.data?.message || "Error Occured!";
        }
      } catch (error) {
        this.showAlert = true;
        console.log("error", error);
        this.errorMsg = "error";
      } finally {
        // this.isPaying = false;
      }
    },
  },
};
</script>

<style lang="scss" scoped></style>
