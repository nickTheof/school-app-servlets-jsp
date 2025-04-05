window.addEventListener("DOMContentLoaded", function () {
        document
          .getElementById("logoutMenuToggler")
          .addEventListener("click", function () {
            document
              .getElementById("dropdownHeaderLogoutMenu")
              .classList.toggle("hidden");
            const expanded = this.getAttribute("aria-expanded") === "true";
            this.setAttribute("aria-expanded", !expanded);
          });
      });