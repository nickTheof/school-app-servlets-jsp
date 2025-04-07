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
    <title>Coding Factory - Αναλυτικά Στοιχεία Καθηγητή</title>
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
               <div class="w-full px-4 md:px-12 xl:px-32 py-6">
                 <div class="bg-white shadow-md rounded-xl p-6 border border-gray-200">
                   <h2 class="text-center text-2xl font-serif font-semibold text-[#762124] mb-6">
                     Στοιχεία Εκπαιδευτή
                   </h2>
                   <c:if test="${not empty requestScope.message}" >
                    <div class="flex justify-center my-6">
                        <div class="w-[90%] md:w-2/3 lg:w-1/2 bg-red-100 border border-red-400 text-red-700 px-6 py-4 rounded-lg shadow-md flex items-start gap-3">
                          <svg class="w-6 h-6 text-red-500 mt-1 shrink-0" fill="none" stroke="currentColor" stroke-width="2"
                               viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M12 9v2m0 4h.01M12 5C7.58 5 4 8.58 4 13s3.58 8 8 8 8-3.58 8-8-3.58-8-8-8z"/>
                          </svg>
                          <div class="text-sm">
                            <strong class="block font-semibold">Σφάλμα!</strong>
                            <span>${requestScope.message}</span>
                          </div>
                        </div>
                    </div>
                   </c:if>
                    <c:if test="${requestScope.teacher != null}">
                   <div class="grid grid-cols-1 md:grid-cols-2 gap-4 text-base text-gray-800">
                     <div class="flex flex-col">
                       <span class="font-semibold text-[#762124]">ID</span>
                       <span>${requestScope.teacher.id}</span>
                     </div>
                     <div class="flex flex-col">
                       <span class="font-semibold text-[#762124]">UUID</span>
                       <span>${requestScope.teacher.uuid}</span>
                     </div>
                     <div class="flex flex-col">
                       <span class="font-semibold text-[#762124]">Όνομα</span>
                       <span>${requestScope.teacher.firstname}</span>
                     </div>
                     <div class="flex flex-col">
                       <span class="font-semibold text-[#762124]">Επώνυμο</span>
                       <span>${requestScope.teacher.lastname}</span>
                     </div>
                     <div class="flex flex-col">
                       <span class="font-semibold text-[#762124]">ΑΦΜ</span>
                       <span>${requestScope.teacher.vat}</span>
                     </div>
                     <div class="flex flex-col">
                       <span class="font-semibold text-[#762124]">Όνομα Πατρός</span>
                       <span>${requestScope.teacher.fatherName}</span>
                     </div>
                     <div class="flex flex-col">
                       <span class="font-semibold text-[#762124]">Τηλέφωνο</span>
                       <span>${requestScope.teacher.phoneNum}</span>
                     </div>
                     <div class="flex flex-col">
                       <span class="font-semibold text-[#762124]">Email</span>
                       <span>${requestScope.teacher.email}</span>
                     </div>
                     <div class="flex flex-col">
                       <span class="font-semibold text-[#762124]">Πόλη</span>
                       <c:forEach var="city" items="${requestScope.cities}">
                            <c:if test="${requestScope.teacher.cityId == city.id}">
                                <span>${city.name}</span>
                            </c:if>
                       </c:forEach>
                     </div>
                     <div class="flex flex-col">
                       <span class="font-semibold text-[#762124]">ΤΚ</span>
                       <span>${requestScope.teacher.zipcode}</span>
                     </div>
                     <div class="flex flex-col">
                       <span class="font-semibold text-[#762124]">Διεύθυνση</span>
                       <span>${requestScope.teacher.street}</span>
                     </div>
                     <div class="flex flex-col">
                       <span class="font-semibold text-[#762124]">Αριθμός</span>
                       <span>${requestScope.teacher.streetNum}</span>
                     </div>
                   </div>
                 </div>
                 </c:if>

               <div
                 class="lg:hidden text-center my-3 text-[#762124] hover:text-[#5c1a1a]"
               >
                 <a
                   class="hover:underline"
                   href="${pageContext.request.contextPath}/school-app/dashboard"
                   >Επιστροφή στην Αρχική σελίδα</a
                 >
               </div>
             </main>
           </div>
    <%@ include file="/WEB-INF/jsp/footer.jsp" %>

  </body>
</html>
