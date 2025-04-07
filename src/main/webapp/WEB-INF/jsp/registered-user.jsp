<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
  response.setHeader("Pragma", "no-cache");
  response.setHeader("Expires", "0");
%>

<!DOCTYPE html>
<html lang="el">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Coding Factory - Επιτυχής Εγγραφή</title>
  </head>
  <body>
    <%@ include file="/WEB-INF/jsp/header.jsp" %>
    <main
      class="min-h-[calc(100vh-200px)] border-y-4 border-[#5c1a1a] flex justify-center items-center px-4"
    >
      <div
        class="bg-green-100 border border-green-400 text-green-900 rounded-lg shadow-md max-w-xl w-full p-6 space-y-4"
      >
        <div class="flex items-center justify-center space-x-3">
          <span class="text-2xl text-green-600">
            <i class="fa-solid fa-circle-check"></i>
          </span>
          <h2 class="text-xl md:text-2xl font-serif font-medium text-center">
            Επιτυχής Εγγραφή
          </h2>
        </div>

        <p class="text-center text-base md:text-lg">
          Ο χρήστης με Username:
          <strong class="font-semibold">
            ${sessionScope.userInfo.username}
          </strong>
          δημιουργήθηκε επιτυχώς.
        </p>

        <p class="text-center text-base md:text-lg">
          Μπορείτε να συνδεθείτε στην εφαρμογή μέσω του ακόλουθου συνδέσμου.
        </p>

        <div class="text-center text-lg mt-2">
          <a
            class="text-[#762124] hover:text-[#5c1a1a] hover:underline font-medium"
            href="${pageContext.request.contextPath}/login"
          >
            Σύνδεση
          </a>
        </div>
      </div>
    </main>
    <%@ include file="/WEB-INF/jsp/footer.jsp" %>
  </body>
</html>
