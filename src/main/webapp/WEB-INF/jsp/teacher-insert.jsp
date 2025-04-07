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
    <title>Coding Factory - Εισαγωγή Καθηγητή</title>
  </head>
  <body>
    <%@ include file="/WEB-INF/jsp/header.jsp" %>
    <div class="flex min-h-[calc(100vh-200px)] border-y-4 border-[#5c1a1a]">
      <aside class="hidden lg:block w-1/5 bg-[#762124]">
        <nav class="text-lg">
          <ul class="flex flex-col mt-[30px] ps-5 gap-y-2">
            <li>
              <a class="text-white text-lg hover:text-yellow-400 block" href="${pageContext.request.contextPath}/school-app/dashboard">
                Αρχική
              </a>
            </li>
            <li class="text-white">
              Μητρώο Εκπαιδευτών
              <ul class="ps-3 text-base">
                <li>
                  <a class="hover:text-yellow-400 block" href="${pageContext.request.contextPath}/school-app/teachers/view">
                    Προβολή Εκπαιδευτών
                  </a>
                </li>
                <c:if test="${sessionScope.role == 'ADMIN'}">
                  <li>
                    <a class="hover:text-yellow-400 block" href="${pageContext.request.contextPath}/school-app/teachers/insert">
                      Εισαγωγή Εκπαιδευτή
                    </a>
                  </li>
                </c:if>
              </ul>
            </li>
          </ul>
        </nav>
      </aside>
      <main class="w-full lg:w-4/5 bg-[#ECEBEC]">
        <div class="text-center text-black text-2xl my-[30px]">
          Στοιχεία Καθηγητή
        </div>
        <c:if test="${not empty sessionScope.message}">
          <div class="flex justify-center my-6">
            <div class="w-[90%] md:w-2/3 lg:w-1/2 bg-red-100 border border-red-400 text-red-700 px-6 py-4 rounded-lg shadow-md flex items-start gap-3">
              <svg class="w-6 h-6 text-red-500 mt-1 shrink-0" fill="none" stroke="currentColor" stroke-width="2"
                   viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round">
                <path d="M12 9v2m0 4h.01M12 5C7.58 5 4 8.58 4 13s3.58 8 8 8 8-3.58 8-8-3.58-8-8-8z"/>
              </svg>
              <div class="text-sm">
                <strong class="block font-semibold">Σφάλμα!</strong>
                <span>${sessionScope.message}</span>
              </div>
            </div>
          </div>
        </c:if>
        <form method="POST" action="${pageContext.request.contextPath}/school-app/teachers/insert">
          <div class="p-1 flex flex-wrap justify-center items-center gap-x-1 gap-y-3">
            <div class="w-4/5 mx-auto lg:w-2/5 lg:mx-0 border h-16">
              <div class="flex flex-row items-center justify-center lg:justify-start gap-x-3">
                <label for="firstname" class="font-bold text-sm w-[140px]">Όνομα *</label>
                <input
                  id="firstname"
                  name="firstname"
                  value="${requestScope.insertDTOInfo.firstname}"
                  type="text"
                  class="h-10 border border-black w-[300px] p-2 shadow-xl focus:outline-none focus:ring-2 focus:ring-inset-2"
                  required
                />
              </div>
              <div class="text-red-600 text-sm mt-1 text-center lg:text-start">
                <span>${requestScope.firstnameError}</span>
              </div>
            </div>

            <div class="w-4/5 mx-auto lg:w-2/5 lg:mx-0 border h-16">
              <div class="flex flex-row items-center justify-center lg:justify-start gap-x-3">
                <label for="lastname" class="font-bold text-sm w-[140px]">Επώνυμο *</label>
                <input
                  id="lastname"
                  name="lastname"
                  value="${requestScope.insertDTOInfo.lastname}"
                  type="text"
                  class="w-[300px] h-10 border border-black p-2 shadow-xl focus:outline-none focus:ring-2 focus:ring-inset-2"
                  required
                />
              </div>
              <div class="text-red-600 text-sm mt-1 text-start md:text-center lg:text-start">
                <span>${requestScope.lastnameError}</span>
              </div>
            </div>

            <div class="w-4/5 mx-auto lg:w-2/5 lg:mx-0 border h-16">
              <div class="flex flex-row items-center justify-center lg:justify-start gap-x-3">
                <label for="vat" class="font-bold text-sm w-[140px]">ΑΦΜ *</label>
                <input
                  id="vat"
                  name="vat"
                  value="${requestScope.insertDTOInfo.vat}"
                  type="text"
                  class="w-[300px] h-10 border border-black p-2 shadow-xl focus:outline-none focus:ring-2 focus:ring-inset-2"
                  required
                />
              </div>
              <div class="text-red-600 text-sm mt-1 text-start md:text-center lg:text-start">
                <span>${requestScope.vatError}</span>
              </div>
            </div>

            <div class="w-4/5 mx-auto lg:w-2/5 lg:mx-0 border h-16">
              <div class="flex flex-row items-center justify-center lg:justify-start gap-x-3">
                <label for="fatherName" class="font-bold text-sm w-[140px]">Πατρώνυμο *</label>
                <input
                  id="fatherName"
                  name="fatherName"
                  value="${requestScope.insertDTOInfo.fatherName}"
                  type="text"
                  class="h-10 border border-black p-2 shadow-xl focus:outline-none focus:ring-2 focus:ring-inset-2 w-[300px]"
                  required
                />
              </div>
              <div class="text-red-600 text-sm mt-1 text-start md:text-center lg:text-start">
                <span>${requestScope.fatherNameError}</span>
              </div>
            </div>

            <div class="w-4/5 mx-auto lg:w-2/5 lg:mx-0 border h-16">
              <div class="flex flex-row items-center justify-center lg:justify-start gap-x-3">
                <label for="phoneNumber" class="font-bold text-sm w-[140px]">Τηλέφωνο *</label>
                <input
                  id="phoneNumber"
                  name="phoneNum"
                  value="${requestScope.insertDTOInfo.phoneNum}"
                  type="text"
                  class="w-[300px] h-10 border border-black p-2 shadow-xl focus:outline-none focus:ring-2 focus:ring-inset-2"
                  required
                />
              </div>
              <div class="text-red-600 text-sm mt-1 text-start md:text-center lg:text-start">
                <span>${requestScope.phoneNumError}</span>
              </div>
            </div>

            <div class="w-4/5 mx-auto lg:w-2/5 lg:mx-0 border h-16">
              <div class="flex flex-row items-center justify-center lg:justify-start gap-x-3">
                <label for="email" class="w-[140px] font-bold text-sm">Email *</label>
                <input
                  id="email"
                  name="email"
                  value="${requestScope.insertDTOInfo.email}"
                  type="email"
                  class="w-[300px] h-10 border border-black p-2 shadow-xl focus:outline-none focus:ring-2 focus:ring-inset-2"
                  required
                />
              </div>
              <div class="text-red-600 text-sm mt-1 text-start md:text-center lg:text-start">
                <span>${requestScope.emailError}</span>
              </div>
            </div>

            <div class="w-4/5 mx-auto lg:w-2/5 lg:mx-0 border h-16">
              <div class="flex flex-row items-center justify-center lg:justify-start gap-x-3">
                <label for="address" class="w-[140px] font-bold text-sm">Διεύθυνση *</label>
                <input
                  id="street"
                  name="street"
                  value="${requestScope.insertDTOInfo.street}"
                  type="text"
                  class="w-[300px] h-10 border border-black p-2 shadow-xl focus:outline-none focus:ring-2 focus:ring-inset-2"
                  required
                />
              </div>
              <div class="text-red-600 text-sm mt-1 text-start md:text-center lg:text-start">
                <span>${requestScope.streetError}</span>
              </div>
            </div>

            <div class="w-4/5 mx-auto lg:w-2/5 lg:mx-0 border h-16">
              <div class="flex flex-row items-center justify-center lg:justify-start gap-x-3">
                <label for="streetNum" class="w-[140px] font-bold text-sm">Αριθμός *</label>
                <input
                  id="streetNum"
                  name="streetNum"
                  value="${requestScope.insertDTOInfo.streetNum}"
                  type="text"
                  class="w-[300px] h-10 border border-black p-2 shadow-xl focus:outline-none focus:ring-2 focus:ring-inset-2"
                  required
                />
              </div>
              <div class="text-red-600 text-sm mt-1 text-start md:text-center lg:text-start">
                <span>${requestScope.streetNumError}</span>
              </div>
            </div>

            <div class="w-4/5 mx-auto lg:w-2/5 lg:mx-0 border h-16">
              <div class="flex flex-row items-center justify-center lg:justify-start gap-x-3">
                <label for="city" class="w-[140px] font-bold text-sm">Πόλη *</label>
                <select
                  id="city"
                  name="cityId"
                  class="w-[300px] h-10 border border-black p-2 shadow-xl focus:outline-none focus:ring-2 focus:ring-inset-2"
                  required
                >
                  <option value="" disabled ${empty requestScope.insertDTOInfo.cityId ? 'selected' : ''}>
                    Επιλέξτε Πόλη
                  </option>
                  <c:forEach var="city" items="${requestScope.cities}">
                    <option value="${city.id}" ${city.id eq requestScope.insertDTOInfo.cityId ? 'selected' : ''}>
                      ${city.name}
                    </option>
                  </c:forEach>
                </select>
              </div>
              <div class="text-red-600 text-sm mt-1 text-start md:text-center lg:text-start">
                <span>${requestScope.cityError}</span>
              </div>
            </div>

            <div class="w-4/5 mx-auto lg:w-2/5 lg:mx-0 border h-16">
              <div class="flex flex-row items-center justify-center lg:justify-start gap-x-3">
                <label for="zipcode" class="w-[140px] font-bold text-sm">ΤΚ *</label>
                <input
                  id="zipcode"
                  name="zipcode"
                  value="${requestScope.insertDTOInfo.zipcode}"
                  type="text"
                  class="w-[300px] h-10 border border-black p-2 shadow-xl focus:outline-none focus:ring-2 focus:ring-inset-2"
                  required
                />
              </div>
              <div class="text-red-600 text-sm mt-1 text-start md:text-center lg:text-start">
                <span>${requestScope.zipcodeError}</span>
              </div>
            </div>

            <button
              class="w-[300px] h-[50px] mb-[10px] font-medium text-white bg-[#762124] hover:bg-[#5c1a1a]"
              type="submit">
              Υποβολή
            </button>
          </div>
        </form>
        <div class="lg:hidden text-center text-[#762124] hover:text-[#5c1a1a] mb-[10px]">
          <a class="hover:underline" href="${pageContext.request.contextPath}/school-app/dashboard">
            Επιστροφή στην Αρχική σελίδα
          </a>
        </div>
      </main>
    </div>
    <%@ include file="/WEB-INF/jsp/footer.jsp" %>
  </body>
</html>
