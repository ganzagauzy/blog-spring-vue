<template>
  <div class="px-4 md:px-8 relative overflow-y-auto">
    <div class="overflow-y-auto">
      <!-- modals -->
      <!-- createupdatemodal -->
      <div class="overflow-y-auto">
        <div
          v-if="modal1"
          class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-70 z-50 overflow-y-auto"
        >
          <div class="relative p-4 w-[310px] md:w-[600px]">
            <!-- Modal content -->
            <div
              class="mt-64 md:mt-0 relative bg-white rounded-lg shadow-lg dark:bg-gray-700"
            >
              <!-- Modal header -->
              <div
                class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600"
              >
                <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                  {{ isEdit ? "Update blog" : "Save blog" }}
                </h3>
                <button
                  type="button"
                  @click="modal1 = false"
                  class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
                >
                  <svg
                    class="w-3 h-3"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 14 14"
                  >
                    <path
                      stroke="currentColor"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                    />
                  </svg>
                  <span class="sr-only">Close modal</span>
                </button>
              </div>
              <!-- Modal body -->
              <div class="p-4 md:p-5">
                <form class="space-y-6" action="" @submit.prevent="">
                  <div class="">
                    <div>
                      <label
                        for="rate"
                        class="block mb-1text-sm font-medium text-gray-900 dark:text-white"
                        >Blog title</label
                      >
                      <input
                        type="text"
                        name="title"
                        id="title"
                        class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-indigo-400 focus:border-indigo-400 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                        placeholder="title"
                        required
                        v-model="title"
                      />
                    </div>
                  </div>

                  <div class="">
                    <div>
                      <label
                        for="images"
                        class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                        >Blog images</label
                      >
                      <textarea
                        id="images"
                        rows="2"
                        class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-indigo-600 focus:border-indigo-600 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-indigo-600 dark:focus:border-indigo-600"
                        placeholder="Put image urls here and add camma between them, example: image1,image2,image3, ..."
                        v-model="images"
                      ></textarea>
                    </div>
                  </div>
                  <div class="">
                    <div>
                      <label
                        for="description"
                        class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                        >Blog description</label
                      >
                      <textarea
                        id="images"
                        rows="2"
                        class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-indigo-600 focus:border-indigo-600 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-indigo-600 dark:focus:border-indigo-600"
                        placeholder="Write blog details here..."
                        v-model="description"
                      ></textarea>
                    </div>
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
                    v-if="!isEdit"
                    @click="saveBlog"
                    type="submit"
                    href="dashboard"
                    class="w-full text-white bg-indigo-600 hover:bg-indigo-600 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-gray-600 dark:hover:bg-gray-700 dark:focus:ring-gray-800"
                    :class="{
                      'cursor-not-allowed opacity-50': isLoading,
                    }"
                    :disabled="isLoading"
                  >
                    {{ isLoading ? "Loading..." : "Save blog" }}
                  </button>
                  <button
                    v-else
                    @click="updateBlog"
                    type="submit"
                    href="dashboard"
                    class="w-full text-white bg-indigo-600 hover:bg-indigo-600 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-gray-600 dark:hover:bg-gray-700 dark:focus:ring-gray-800"
                    :class="{
                      'cursor-not-allowed opacity-50': isLoading,
                    }"
                    :disabled="isLoading"
                  >
                    {{ isLoading ? "Loading..." : "Update blog" }}
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
        <div class="">
          <div
            v-if="modal2"
            class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-70 z-50"
          >
            <div class="relative p-4 w-full max-w-md max-h-full">
              <div
                class="relative bg-white rounded-lg shadow-lg dark:bg-gray-700"
              >
                <button
                  @click="modal2 = false"
                  type="button"
                  class="absolute top-3 end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
                >
                  <svg
                    class="w-3 h-3"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 14 14"
                  >
                    <path
                      stroke="currentColor"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
                    />
                  </svg>
                  <span class="sr-only">Close modal</span>
                </button>
                <div class="p-4 md:p-5 text-center">
                  <svg
                    class="mx-auto mb-4 text-gray-400 w-12 h-12 dark:text-gray-200"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 20 20"
                  >
                    <path
                      stroke="currentColor"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M10 11V6m0 8h.01M19 10a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
                    />
                  </svg>
                  <h3
                    class="mb-5 text-lg font-normal text-gray-600 dark:text-gray-400"
                  >
                    Are you sure you want to delete this item?
                  </h3>
                  <button
                    @click="confirmDelete"
                    type="button"
                    class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center me-2"
                  >
                    {{ isLoading ? "Loading..." : "Yes, I'm sure" }}
                  </button>
                  <button
                    @click="modal2 = false"
                    type="button"
                    class="text-gray-600 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10 dark:bg-gray-700 dark:text-gray-300 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-600 dark:focus:ring-gray-600"
                  >
                    No, cancel
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- deletemodal -->
    </div>
    <section class="bg-white dark:bg-gray-900 py-5 rounded">
      <div class="">
        <!-- Start coding here -->

        <div
          class="bg-white dark:bg-gray-800 relative shadow-md sm:rounded-lg overflow-hidden"
        >
          <div class="px-4 py-2 border-b flex justify-between flex-wrap">
            <div>
              <h1 class="font-bold text-1xl">
                All Blogs
                <span class="text-xs font-semi-bold px-3" v-if="blogs"
                  >{{ blogs.length }} results</span
                >
              </h1>
            </div>
            <div>
              <button
                type="button"
                class="text-white bg-indigo-600 hover:bg-indigo-600 focus:outline-none focus:ring-4 focus:ring-indigo-300 font-medium rounded text-sm px-5 py-1 text-center mr-2 mb-2 dark:bg-indigo-600 dark:hover:bg-indigo-600 dark:focus:ring-indigo-800"
                @click="getBlogs"
              >
                Refresh
              </button>
            </div>
          </div>
          <div
            class="flex flex-col md:flex-row items-center justify-between space-y-3 md:space-y-0 md:space-x-4 p-4"
          >
            <div class="w-full md:w-1/2"></div>
            <div
              class="w-full md:w-auto flex flex-col md:flex-row space-y-2 md:space-y-0 items-stretch md:items-center justify-end md:space-x-3 flex-shrink-0"
            >
              <div class="flex items-center space-x-3 w-full md:w-auto">
                <button
                  type="button"
                  class="text-white bg-indigo-600 hover:bg-indigo-600 font-medium rounded py-1 mt-1 px-4 text-sm text-center mr-2 mb-2 dark:bg-indigo-600 dark:hover:bg-indigo-600"
                  @click="openModel1"
                >
                  + New blog
                </button>
              </div>
            </div>
          </div>
          <div class="overflow-x-auto">
            <table
              class="w-full text-sm text-left text-gray-600 dark:text-gray-400"
            >
              <thead
                class="text-xs text-gray-700 capitalize bg-gray-50 dark:bg-gray-700 dark:text-gray-400"
              >
                <tr>
                  <th scope="col" class="px-4 py-3">Title</th>
                  <th scope="col" class="px-4 py-3">Images</th>
                  <th scope="col" class="px-4 py-3">Description</th>
                  <th scope="col" class="px-4 py-3">
                    <span class="sr-only">Actions</span>
                  </th>
                </tr>
              </thead>
              <div v-if="isLoading" class="pl-5 py-5">Fetching data...</div>
              <tbody v-if="blogs">
                <tr
                  class="border-b dark:border-gray-700"
                  v-for="(blog, i) in blogs"
                  :key="i"
                >
                  <th
                    scope="row"
                    class="px-4 py-3 font-medium text-gray-900 whitespace-nowrap dark:text-white"
                  >
                    {{ blog.title }}
                  </th>
                  <td class="px-4 py-3">{{ blog.images.length }}</td>
                  <td class="px-4 py-3">{{ blog.content }}</td>

                  <td class="px-4 py-3 flex items-center justify-end">
                    <button
                      @click="editItem(blog)"
                      id="apple-imac-27-dropdown-button"
                      data-dropdown-toggle="apple-imac-27-dropdown"
                      class="inline-flex items-center p-0.5 text-sm font-medium text-center text-gray-600 hover:text-gray-800 rounded-lg focus:outline-none dark:text-gray-400 dark:hover:text-gray-100"
                      type="button"
                    >
                      <svg
                        class="w-4 h-4"
                        aria-hidden="true"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="currentColor"
                        viewBox="0 0 20 18"
                      >
                        <path
                          d="M12.687 14.408a3.01 3.01 0 0 1-1.533.821l-3.566.713a3 3 0 0 1-3.53-3.53l.713-3.566a3.01 3.01 0 0 1 .821-1.533L10.905 2H2.167A2.169 2.169 0 0 0 0 4.167v11.666A2.169 2.169 0 0 0 2.167 18h11.666A2.169 2.169 0 0 0 16 15.833V11.1l-3.313 3.308Zm5.53-9.065.546-.546a2.518 2.518 0 0 0 0-3.56 2.576 2.576 0 0 0-3.559 0l-.547.547 3.56 3.56Z"
                        />
                        <path
                          d="M13.243 3.2 7.359 9.081a.5.5 0 0 0-.136.256L6.51 12.9a.5.5 0 0 0 .59.59l3.566-.713a.5.5 0 0 0 .255-.136L16.8 6.757 13.243 3.2Z"
                        />
                      </svg>
                    </button>
                    <div class="mx-2"></div>
                    <button
                      @click="deleteItem(blog)"
                      id="apple-imac-27-dropdown-button"
                      data-dropdown-toggle="apple-imac-27-dropdown"
                      class="inline-flex items-center p-0.5 text-sm font-medium text-center text-gray-600 hover:text-gray-800 rounded-lg focus:outline-none dark:text-gray-400 dark:hover:text-gray-100"
                      type="button"
                    >
                      <svg
                        class="w-4 h-4 text-red-600"
                        aria-hidden="true"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 18 20"
                      >
                        <path
                          stroke="currentColor"
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M1 5h16M7 8v8m4-8v8M7 1h4a1 1 0 0 1 1 1v3H6V2a1 1 0 0 1 1-1ZM3 5h12v13a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V5Z"
                        />
                      </svg>
                    </button>
                    <div class="mx-2"></div>
                    <nuxt-link
                      :to="'/admin/blogs/' + blog.id"
                      id="apple-imac-27-dropdown-button"
                      data-dropdown-toggle="apple-imac-27-dropdown"
                      class="inline-flex items-center p-0.5 text-sm font-medium text-center text-gray-600 hover:text-gray-800 rounded-lg focus:outline-none dark:text-gray-400 dark:hover:text-gray-100"
                      type="button"
                    >
                      <svg
                        class="w-4 h-4 dark:text-white"
                        aria-hidden="true"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="currentColor"
                        viewBox="0 0 20 14"
                      >
                        <path
                          d="M10 0C4.612 0 0 5.336 0 7c0 1.742 3.546 7 10 7 6.454 0 10-5.258 10-7 0-1.664-4.612-7-10-7Zm0 10a3 3 0 1 1 0-6 3 3 0 0 1 0 6Z"
                        />
                      </svg>
                    </nuxt-link>
                  </td>
                </tr>
              </tbody>
            </table>
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
            <div class="text-center flex" v-if="last_page > 1">
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
                    @click="goFirst"
                    class="flex items-center justify-center text-sm py-2 px-3 leading-tight text-gray-600 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                    :class="{
                      'bg-green-800 green-bg text-white': page == 1,
                    }"
                  >
                    1
                  </button>
                </li>
                <li v-if="page >= 3">
                  <button
                    class="flex items-center justify-center text-sm py-2 px-3 leading-tight text-gray-600 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  >
                    {{ ".." }}
                  </button>
                </li>
                <div v-for="n in 3" :key="n">
                  <li v-if="page + n - 1 > 1 && page + n - 1 < last_page">
                    <button
                      class="flex items-center justify-center text-sm py-2 px-2.5 leading-tight text-gray-600 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                      :class="{
                        'bg-green-800 green-bg text-white':
                          page == page + n - 1,
                      }"
                      @click="goTopage(page + n - 1)"
                    >
                      {{ page + n - 1 }}
                    </button>
                  </li>
                </div>

                <li v-if="page < last_page - 2">
                  <button
                    class="flex items-center justify-center text-sm py-2 px-3 leading-tight text-gray-600 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                  >
                    ..
                  </button>
                </li>
                <li>
                  <button
                    @click="goLast"
                    class="flex items-center justify-center text-sm py-2 px-3 leading-tight text-gray-600 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                    :class="{
                      'bg-green-800 green-bg text-white': page == last_page,
                    }"
                  >
                    {{ last_page }}
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
        </div>
      </div>
    </section>
  </div>
</template>

<script lang="ts">
import { useAxios } from "../../composables/useAxios";
import moment from "moment";
export default {
  data() {
    return {
      modal1: false,
      modal2: false,
      headerActions: false,
      headerFilter: false,
      blogs: [] as any,
      categories: [] as any,
      selectedItem: {} as any,
      isEdit: false,
      isLoading: false,
      showAlert: false,
      ascend: false,
      descend: false,
      title: "",
      images: "",
      plocation: "",
      pstatus: "",
      pcategory: "",
      ppreview: "",
      plink: "",
      description: "",
      pamount: "",
      filterBy: "asc",
      errorMsg: "",
      filters: {
        from: moment().subtract(1, "month"),
        to: moment().format(),
      },
      from: 0,
      to: 0,
      total: 0,
      per_page: 0,
      page: 1,
      last_page: 1,
    };
  },
  computed: {
    userInfo() {
      const userInfo = localStorage.getItem("user_details");
      const parsedUserInfo = userInfo ? JSON.parse(userInfo) : null;

      if (parsedUserInfo == null || !parsedUserInfo.token) {
        return {};
      } else {
        return parsedUserInfo;
      }
    },
  },
  created() {
    this.getBlogs();
  },
  methods: {
    openModel1() {
      this.clearForm();
      (this.isEdit = false), (this.modal1 = true);
    },
    async getBlogs() {
      this.blogs = [];
      try {
        this.isLoading = true;
        const { data, error, pending } = await useAxios(
          `posts/author/${this.userInfo.user.id}`,
          {
            method: "GET",
          }
        );
        // console.log("response", data);
        if (error.value) {
          this.showAlert = true;
          this.errorMsg = error.value.data.errors;
        }
        if (data.value) {
          console.log("blogs", data.value);
          this.blogs = data.value;
          return;
          this.from = data.value?.results.from;
          this.to = data.value?.results.to;
          this.total = data.value?.results.total;
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

    async saveBlog() {
      try {
        this.isLoading = true;
        this.showAlert = false;
        // console.log("images", this.images);
        let images = [];
        images = this.images.split(",");
        // console.log("array of imags", images);
        const { data, error, pending } = await useAxios("posts", {
          method: "POST",
          body: {
            title: this.title,
            content: this.description,
            author: this.userInfo.user,
            images: images,
          },
        });

        if (data.value) {
          this.getBlogs();
          this.modal1 = false;
          // console.log("data", data.value);
        } else {
          this.showAlert = true;
          // console.log("error", error.value?.images);
          // this.errorMsg = error || "Error Occured!";
        }
      } catch (error) {
        this.showAlert = true;
        console.log("error", error);
        this.errorMsg = "error";
      } finally {
        this.isLoading = false;

        this.clearForm();
      }
    },
    async updateBlog() {
      try {
        this.isLoading = true;
        this.showAlert = false;
        let images = [];
        images = this.images.split(",");
        // console.log("new img", this.images);

        const { data, error, pending } = await useAxios(
          `posts/${this.selectedItem.id}`,
          {
            method: "PUT",
            body: {
              title: this.title,
              content: this.description,
              author: this.userInfo.user,
              images: images,
            },
          }
        );

        if (data.value) {
          // console.log("data", data.value);
          (this.isEdit = false), this.getBlogs();
          this.modal1 = false;
        } else {
          this.showAlert = true;
          console.log("error", error.value);
          // this.errorMsg = error || "Error Occured!";
        }
      } catch (error) {
        this.showAlert = true;
        console.log("error", error);
        this.errorMsg = "error";
      } finally {
        this.isLoading = false;

        this.clearForm();
      }
    },
    async confirmDelete() {
      try {
        this.isLoading = true;
        this.showAlert = false;
        const { data, error, pending } = await useAxios(
          `posts/${this.selectedItem.id}`,
          {
            method: "DELETE",
          }
        );

        if (data.value) {
          // console.log("data", data.value);
        } else {
          this.showAlert = true;
          console.log("error", error.value);
          // this.errorMsg = error || "Error Occured!";
        }
      } catch (error) {
        this.showAlert = true;
        console.log("error", error);
        this.errorMsg = "error";
      } finally {
        this.isLoading = false;
        this.getBlogs();
        this.modal2 = false;
        this.isEdit = false;
        this.clearForm();
      }
    },
    editItem(item: any) {
      // console.log("item", item);
      this.showAlert = false;

      this.selectedItem = item;
      (this.title = item.title), (this.images = item.images.toString());
      // (this.images = item.images),
      (this.description = item.content), (this.isEdit = true);
      this.modal1 = true;
    },
    deleteItem(item: any) {
      // console.log("hii");
      this.selectedItem = item;
      this.modal1 = false;
      this.modal2 = true;
    },
    clearForm() {
      (this.title = ""), (this.images = ""), (this.description = "");
    },
  },
};
</script>

<style lang="scss" scoped></style>
