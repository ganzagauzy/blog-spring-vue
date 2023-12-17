export default defineNuxtRouteMiddleware((to, from) => {
  const userInfo = localStorage.getItem("user_details");
  const parsedUserInfo = userInfo ? JSON.parse(userInfo) : null;

  if (parsedUserInfo == null || !parsedUserInfo.token) {
    localStorage.clear();
    return (window.location.href = "/login");
  }

  // if (parsedUserInfo.user.is_subscribed == false) {
  //     if (to.path != "/admin/profile") {
  //         return navigateTo("/admin/profile");
  //     }
  // }
});
