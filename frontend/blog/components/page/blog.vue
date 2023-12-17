<template>
  <div>
    <div v-if="showAlert" class="">
      <div class="flex justify-end">
        <svg
          @click="showAlert = false"
          class="w-4 h-4 text-red dark:text-white cursor-pointer mb-1"
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
              v-for="(blog, index) in paginatedBlogs"
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
        <nav
          class="flex flex-col md:flex-row justify-between items-start md:items-center space-y-3 md:space-y-0 p-4"
          aria-label="Table navigation"
        >
          <!-- <span class="text-sm font-normal text-gray-600 dark:text-gray-400">
              Showing
              <span class="font-semibold text-gray-900 dark:text-white"
                >{{ from }}-{{ to }}</span
              >
              of
              <span class="font-semibold text-gray-900 dark:text-white">{{
                total
              }}</span>
            </span> -->
          <div class="text-center flex">
            <ul class="inline-flex items-stretch -space-x-px">
              <li>
                <button
                  @click="getPrevious"
                  class="flex items-center justify-center h-full py-1.5 px-2 ml-0 text-gray-600 bg-white rounded-l-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                >
                  <span class="sr-only">Previous</span>
                  <svg
                    class="w-4 h-4"
                    aria-hidden="true"
                    fill="currentColor"
                    viewbox="0 0 20 20"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z"
                      clip-rule="evenodd"
                    />
                  </svg>
                </button>
              </li>
              <li>
                <button
                  class="flex items-center justify-center text-sm py-2 px-3 leading-tight text-white bg-indigo-700 border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                >
                  {{ page }}
                </button>
              </li>

              <li>
                <button
                  @click="getNext"
                  class="flex items-center justify-center h-full py-1.5 px-2 leading-tight text-gray-600 bg-white rounded-r-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                >
                  <span class="sr-only">Next</span>
                  <svg
                    class="w-4 h-4"
                    aria-hidden="true"
                    fill="currentColor"
                    viewbox="0 0 20 20"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z"
                      clip-rule="evenodd"
                    />
                  </svg>
                </button>
              </li>
            </ul>
          </div>
        </nav>
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
      per_page: 2,
      page: 1,
      last_page: 1,
      email: "",
      searchQuery: "",
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
  computed: {
    paginatedBlogs() {
      const start = (this.page - 1) * this.totalPages;
      const end = start + this.per_page;
      console.log("blogs p", this.blogs.slice(start, end));

      return this.blogs.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.blogs.length / this.per_page);
    },
    filteredPosts() {
      // Filter posts based on the search query
      const query = this.searchQuery.toLowerCase();
      return this.paginatedBlogs.filter((post: any) => {
        return (
          post.title.toLowerCase().includes(query) ||
          post.body.toLowerCase().includes(query)
        );
      });
    },
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
          this.errorMsg = "Error occured refresh";
        }
      } catch (error) {
        // console.log("error", error);
      } finally {
        this.isLoading = false;
      }
    },
    getPrevious() {
      if (this.page > 1) {
        this.page--;
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
      if (this.page < this.totalPages) {
        this.page++;
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
  },
};
</script>

<style lang="scss" scoped></style>
