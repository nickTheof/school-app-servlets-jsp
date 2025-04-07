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
        <main class="min-h-[calc(100vh-200px)] border-y-4 border-[#5c1a1a]">
            <div class="flex min-h-[calc(100vh-200px)] border-y-4 border-[#5c1a1a]">
              <aside class="hidden lg:block w-1/5 bg-[#762124]">
                <nav class="text-lg">
                  <ul class="flex flex-col mt-[30px] ps-5 gap-y-2">
                    <li class="text-yellow-400">Αρχική</li>
                    <li class="text-white">
                      Μητρώο Εκπαιδευτών
                      <ul class="ps-3 text-base">
                        <li>
                          <a
                            class="hover:text-yellow-400 block"
                            href="${pageContext.request.contextPath}/school-app/teachers/view"
                            >Προβολή Εκπαιδευτών</a
                          >
                        </li>
                        <c:if test="${sessionScope.role == 'ADMIN'}">
                        <li>
                          <a
                            class="hover:text-yellow-400 block"
                            href="${pageContext.request.contextPath}/school-app/teachers/insert"
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
                  Ποιότητα στην Εκπαίδευση
                </div>
                <div class="flex flex-col items-start ps-[50px]">
                  <span class="font-bold text-lg">Μητρώο Εκπαιδευτών</span>

                  <span class="mb-3">Φόρμα προβολής εκπαιδευτών Coding Factory</span>
                  <a
                    type="button"
                    class="px-[40px] py-[10px] bg-[#762124] hover:bg-[#5c1a1a] text-white text-md rounded-md"
                    href="${pageContext.request.contextPath}/school-app/teachers/view"
                  >
                    Συνέχεια
                  </a>
                  <c:if test="${sessionScope.role == 'ADMIN'}">
                  <span class="my-3"
                    >Φόρμα εισαγωγής εκπαιδευτή στο μητρώο εκπαιδευτών του Coding
                    Factory</span
                  >
                  <a
                    type="button"
                    class="px-[40px] py-[10px] bg-[#762124] hover:bg-[#5c1a1a] text-white text-md rounded-md"
                    href="${pageContext.request.contextPath}/school-app/teachers/insert"
                  >
                    Συνέχεια
                  </a>
                  </c:if>
                </div>
              </main>
            </div>
    <%@ include file="/WEB-INF/jsp/footer.jsp" %>
  </body>
</html>
