<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Coding Factory - Central Service</title>
  </head>
  <body>
    <%@ include file="/WEB-INF/jsp/header.jsp" %>
        <main class="min-h-[calc(100vh-200px)] border-y-4 border-[#5c1a1a]">
          <div class="text-center pt-[50px]">
            <span class="text-black font-serif font-thin text-3xl block"
              >Κεντρική Υπηρεσία Coding Factory</span
            >
          </div>
          <div class="mt-[60px] text-center">
            <a
              class="px-[40px] py-[15px] bg-green-600 hover:bg-green-700 text-white text-md rounded-md"
              type="button"
              href="${pageContext.request.contextPath}/login"
              >Είσοδος</a
            >
          </div>
        </main>
    <%@ include file="/WEB-INF/jsp/footer.jsp" %>
  </body>
</html>
