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
    <title>Coding Factory - Διαγραφή Καθηγητή</title>
  </head>
  <body>
    <%@ include file="/WEB-INF/jsp/header.jsp" %>
    <div class="flex min-h-[calc(100vh-200px)] border-y-4 border-[#5c1a1a]">
      <aside class="hidden lg:block w-1/5 bg-[#762124]">
        <nav class="text-lg">
          <ul class="flex flex-col mt-[30px] ps-5 gap-y-2">
            <li>
              <a
                class="text-white text-lg hover:text-yellow-400 block"
                href="${pageContext.request.contextPath}/school-app/dashboard"
              >
                Αρχική
              </a>
            </li>
            <li class="text-white">
              Μητρώο Εκπαιδευτών
              <ul class="ps-3 text-base">
                <li>
                  <a
                    class="hover:text-yellow-400 block"
                    href="${pageContext.request.contextPath}/school-app/teachers/view"
                  >
                    Προβολή Εκπαιδευτών
                  </a>
                </li>
                <c:if test="${sessionScope.role == 'ADMIN'}">
                  <li>
                    <a
                      class="hover:text-yellow-400 block"
                      href="${pageContext.request.contextPath}/school-app/teachers/insert"
                    >
                      Εισαγωγή Εκπαιδευτή
                    </a>
                  </li>
                </c:if>
              </ul>
            </li>
            <li class="text-white">
              Μητρώο Μαθητών
              <ul class="ps-3 text-base">
                <li>
                  <a class="hover:text-yellow-400 block" href="${pageContext.request.contextPath}/school-app/students/view">Προβολή Μαθητών</a>
                </li>
                <c:if test="${sessionScope.role == 'ADMIN'}">
                  <li>
                    <a class="hover:text-yellow-400 block" href="${pageContext.request.contextPath}/school-app/students/insert">Εισαγωγή Μαθητή</a>
                  </li>
                </c:if>
              </ul>
            </li>
          </ul>
        </nav>
      </aside>
      <main class="w-full lg:w-4/5">
        <div class="text-center text-black font-thin text-2xl my-[30px]">
          Διαγραφή Εκπαιδευτή
        </div>

        <c:if test="${requestScope.id != null}">
          <div
            class="bg-green-100 border border-green-400 text-green-900 rounded-lg shadow-md max-w-xl w-full p-6 space-y-4 mx-auto"
          >
            <div class="flex items-center justify-center space-x-3">
              <span class="text-2xl text-green-600">
                <i class="fa-solid fa-circle-check"></i>
              </span>
              <h2 class="text-xl md:text-2xl font-serif font-medium text-center">
                Επιτυχής Διαγραφή
              </h2>
            </div>

            <p class="text-center text-base md:text-lg">
              Ο Εκπαιδευτής με ID:
              <strong class="font-semibold">
                ${requestScope.id}
              </strong>
              διαγράφηκε επιτυχώς.
            </p>
          </div>
        </c:if>

        <c:if test="${requestScope.error != null}">
          <div class="text-red-600 text-xl font-thin text-center">
            ${requestScope.error}
          </div>
        </c:if>

        <div class="text-center my-3 text-[#762124] hover:text-[#5c1a1a]">
          <a
            class="hover:underline"
            href="${pageContext.request.contextPath}/school-app/teachers/view"
          >
            Επιστροφή στην Προβολή Εκπαιδευτών
          </a>
        </div>
      </main>
    </div>
    <%@ include file="/WEB-INF/jsp/footer.jsp" %>
  </body>
</html>
