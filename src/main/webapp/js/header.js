window.addEventListener("DOMContentLoaded", function () {
  const toggler = document.getElementById("logoutMenuToggler");
  if (toggler) {
    toggler.addEventListener("click", function () {
      const dropdown = document.getElementById("dropdownHeaderLogoutMenu");
      dropdown?.classList.toggle("hidden");
      const expanded = this.getAttribute("aria-expanded") === "true";
      this.setAttribute("aria-expanded", !expanded);
    });
  }
});