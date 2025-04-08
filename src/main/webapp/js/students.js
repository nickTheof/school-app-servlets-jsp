window.addEventListener("DOMContentLoaded", function () {
        document
          .getElementById("resetBtn")
          .addEventListener("click", function (event) {
               event.preventDefault();
               document.getElementById("filterForm").reset();
               document.getElementById("filterForm").submit();
          });
      // JS for deleting student modal
        const deleteLinks = document.querySelectorAll("a[href*='/students/delete']");
        const modal = document.getElementById("deleteModal");
        const confirmBtn = document.getElementById("confirmDeleteBtn");
        const cancelBtn = document.getElementById("cancelDeleteBtn");

        let deleteUrl = "";

        deleteLinks.forEach(link => {
          link.addEventListener("click", e => {
            e.preventDefault();
            deleteUrl = link.getAttribute("href");
            modal.classList.remove("hidden");
            modal.classList.add("flex");
          });
        });

        confirmBtn.addEventListener("click", () => {
          window.location.href = deleteUrl;
        });

        cancelBtn.addEventListener("click", () => {
          modal.classList.remove("flex");
          modal.classList.add("hidden");
          deleteUrl = "";
        });
      });