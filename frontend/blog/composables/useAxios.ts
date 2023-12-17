import { UseFetchOptions } from "nuxt/dist/app/composables";
import { KeyOfRes } from "nuxt/dist/app/composables/asyncData";

export const useAxios = (
  request: any,
  opts:
    | UseFetchOptions<
        unknown,
        (res: unknown) => unknown,
        KeyOfRes<(res: unknown) => unknown>,
        any
      >
    | undefined
) => {
  const config = useRuntimeConfig();

  const userInfo = localStorage.getItem("user_details");
  const parsedUserInfo = userInfo ? JSON.parse(userInfo) : null;
  // console.log("access_token", parsedUserInfo);

  return useFetch(request, {
    baseURL: config.public.apiBase,
    onRequest({ request, options }) {
      if (parsedUserInfo && parsedUserInfo.access_token) {
        options.headers = {
          Authorization: "Bearer " + parsedUserInfo.access_token,
          Accept: "application/json",
        };
      } else {
        options.headers = {
          Accept: "application/json",
        };
      }
    },
    onRequestError({ request, options, error }) {
      // Handle the request errors
      if (error) {
        console.log(error);
      }
    },
    onResponse({ request, response, options }) {
      // Process the response data
      return response._data;
    },
    // onResponseError({ request, response, options }) {
    //   Handle the response errors
    //   if (response.status === 401) {
    //     localStorage.clear();
    //     window.location.href = "/";
    //   }
    // },
    ...opts,
  });
};
