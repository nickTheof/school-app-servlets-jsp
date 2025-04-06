<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <title>Coding Factory - Central Service</title>
  </head>
  <body>
    <%@ include file="/WEB-INF/jsp/header.jsp" %>
        <div class="flex min-h-[calc(100vh-200px)] border-y-4 border-[#5c1a1a]">
              <aside class="hidden lg:block w-1/5 bg-[#762124]">
                <nav class="text-lg">
                  <ul class="flex flex-col mt-[30px] ps-5 gap-y-2">
                    <li>
                      <a class="text-white text-lg hover:text-yellow-400 block" href="${pageContext.request.contextPath}/school-app/dashboard"
                        >Αρχική</a
                      >
                    </li>
                    <li class="text-white">
                      Μητρώο Εκπαιδευτών
                      <ul class="ps-3 text-base">
                        <li class="text-yellow-400">Προβολή Εκπαιδευτών</li>
                        <c:if test="${sessionScope.role == 'ADMIN'}">
                        <li>
                          <a
                            class="hover:text-yellow-400 block"
                            href="./teacher-insert.html"
                            >Εισαγωγή Εκπαιδευτή</a
                          >
                        </li>
                        </c:if>
                      </ul>
                    </li>
                  </ul>
                </nav>
              </aside>
              <main class="w-full lg:w-4/5">
                <div class="text-center text-black font-thin text-2xl my-[30px]">
                  Μητρώο Εκπαιδευτών
                </div>
                <div>
                  <form id="filterForm" method="GET" action="">
                    <div
                      class="flex flex-row gap-x-2 gap-y-1 p-3 justify-center items-center h-16"
                    >
                      <input
                        name="firstname"
                        class="w-1/4 h-8 rounded-md shadow-md border border-black p-2 focus:outline-none focus:ring focus:ring-inset-2 focus-ring-black text-sm"
                        type="text"
                        placeholder="'Ονομα"
                      />
                      <input
                        name="lastname"
                        class="w-1/4 h-8 rounded-md shadow-md border border-black p-2 focus:outline-none focus:ring focus:ring-inset-2 focus-ring-black text-sm"
                        type="text"
                        placeholder="Επώνυμο"
                      />
                      <button
                        type="submit"
                        class="w-1/5 h-8 font-medium text-sm md:text-base text-white bg-[#762124] hover:bg-[#5c1a1a] rounded-md"
                      >
                        Αναζήτηση
                      </button>
                      <button
                        id="resetBtn"
                        type="button"
                        class="w-1/5 h-8 font-medium text-sm md:text-base text-white bg-stone-500 hover:bg-stone-600 rounded-md"
                      >
                        Εκκαθάριση
                      </button>
                    </div>
                  </form>
                </div>
                <div class="overflow-y-auto max-h-[440px]">
                  <table class="w-4/5 mx-auto border-collapse">
                    <thead class="sticky top-0 bg-[#762124] rounded-lg">
                      <tr class="text-white">
                        <th class="p-3 text-left">ID</th>
                        <th class="p-3 text-left">Όνομα</th>
                        <th class="p-3 text-left">Επώνυμο</th>
                        <th class="p-3 text-center">Ενέργειες</th>
                      </tr>
                    </thead>
                    <tbody id="table-body" class="bg-white divide-y">
                      <c:forEach var = "teacher" items = "${requestScope.teachers}">
                      <tr>
                        <td class="p-3 text-stone-600">${teacher.id}</td>
                        <td class="p-3 text-stone-600">${teacher.firstname}</td>
                        <td class="p-3 text-stone-600">${teacher.lastname}</td>
                        <td class="p-3 text-center flex justify-center gap-3">
                          <a
                            href="${pageContext.request.contextPath}/school-app/teachers/view/teacher?id=${teacher.id}"
                            class="text-blue-500"
                          >
                            <i class="fa-solid fa-eye"></i>
                          </a>
                          <c:if test="${sessionScope.role == 'ADMIN'}">
                          <a
                            href="${pageContext.request.contextPath}/school-app/teachers/update?id=${teacher.id}"
                            class="text-green-500"
                          >
                            <i class="fa-regular fa-pen-to-square"></i>
                          </a>
                          <a
                            href="${pageContext.request.contextPath}/school-app/teachers/delete?id=${teacher.id}"
                            class="text-red-500"
                          >
                            <i class="fa-regular fa-trash-can"></i>
                          </a>
                          </c:if>
                        </td>
                      </tr>
                     </c:forEach>
                    </tbody>
                  </table>
                </div>
                <div class="text-center my-3 text-red-600">
                    ${requestScope.message}
                </div>
                <div
                  class="lg:hidden text-center my-3 text-[#762124] hover:text-[#5c1a1a]"
                >
                  <a href="${pageContext.request.contextPath}/school-app/dashboard"
                    >Επιστροφή στην Αρχική σελίδα</a
                  >
                </div>
              </main>
            </div>
                <script src="${pageContext.request.contextPath}/js/teachers.js"></script>
    <%@ include file="/WEB-INF/jsp/footer.jsp" %>

  </body>
</html>
