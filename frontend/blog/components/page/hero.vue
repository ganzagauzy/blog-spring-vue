<template>
  <div class="-mt-10">
    <div>
      <!-- class="bg-center bg-no-repeat bg-[url('~/assets/images/img1.jpeg')] bg-gray-600 bg-blend-multiply md:h-[80vh]" -->
      <section
        class="bg-center bg-no-repeat bg-[url('~/assets/images/i1.jpg')] bg-gray-600 bg-blend-multiply md:h-[80vh] w-full"
        style="
          background-size: cover !important;
          background-position: center !important;
        "
      >
        <div
          class="px-4 mx-auto max-w-screen-xl text-center py-24 md:py-16 lg:py-32"
        >
          <h1
            class="mb-4 text-4xl font-extrabold tracking-tight leading-none text-white md:text-5xl lg:text-6xl"
          >
            Welcome to Guazy Blog.
          </h1>
          <p
            class="mb-8 text-lg font-normal text-gray-300 lg:text-xl sm:px-16 lg:px-48"
          >
            Blogs from Guazy
          </p>
          <div
            class="flex flex-col space-y-4 sm:flex-row sm:justify-center sm:space-y-0 sm:space-x-4"
          >
            <nuxt-link
              to="/blogs"
              class="inline-flex justify-center items-center py-2 px-5 text-base font-medium text-center text-white rounded-lg bg-indigo-600 hover:bg-indigo-600 focus:ring-4 focus:ring-indigo-300 dark:focus:ring-indigo-900"
            >
              Go to blogs
              <svg
                class="w-3.5 h-3.5 ml-2"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 14 10"
              >
                <path
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M1 5h12m0 0L9 1m4 4L9 9"
                />
              </svg>
            </nuxt-link>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script lang="ts">
export default {};
</script>

<style lang="scss" scoped></style>
