window.addEventListener("DOMContentLoaded", function () {
        document
          .getElementById("resetBtn")
          .addEventListener("click", function (event) {
               event.preventDefault();
               document.getElementById("filterForm").reset();
               document.getElementById("filterForm").submit();
          });
      });