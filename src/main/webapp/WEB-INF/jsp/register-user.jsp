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
    <title>Coding Factory - Φόρμα Εισόδου</title>
  </head>
  <body>
    <%@ include file="/WEB-INF/jsp/header.jsp" %>
        <main class="min-h-[calc(100vh-200px)] border-y-4 border-[#5c1a1a]">
              <div class="w-full p-[15px] md:w-[500px] md:mx-auto rounded-md">
                <div class="text-center mt-[20px] text-3xl font-thin h-16">
                  <span class="block">Φόρμα Εγγραφής Νέου Χρήστη</span>
                </div>
                <div class="text-red-600 text-sm text-center">
                   ${requestScope.errorMessage}
                </div>
                <form method="POST" action="${pageContext.request.contextPath}/register" class="p-4">
                  <div class="flex flex-col border-b-2 mb-2 h-[90px]">
                    <label class="text-sm font-sans mb-1" for="username">Email</label>
                    <div class="flex items-center gap-x-[10px]">
                      <span class="text-stone-400"
                        ><i class="fa-solid fa-user"></i
                      ></span>
                      <input
                        class="grow h-10 p-2 text-sm placeholder:font-sans placeholder:text-sm focus:outline-none focus:ring focus:ring-inset-2 focus:ring-[#762124]"
                        id="username"
                        name="username"
                        type="email"
                        placeholder="Εισάγετε το email σας"
                        value="${requestScope.userRegisterDTO.username}"
                        required
                      />
                    </div>
                    <span class="block text-red-600 text-sm"
                      >${requestScope.usernameMessage}</span
                    >
                  </div>
                  <div class="flex flex-col border-b-2 mb-2 h-[90px]">
                    <label class="text-sm font-sans mb-1" for="password">Κωδικός</label>
                    <div class="flex items-center gap-x-[10px]">
                      <span class="text-stone-400"
                        ><i class="fa-solid fa-lock"></i
                      ></span>
                      <input
                        class="grow h-10 p-2 text-sm placeholder:font-sans placeholder:text-sm focus:outline-none focus:ring focus:ring-inset-2 focus:ring-[#762124]"
                        id="password"
                        type="password"
                        name="password"
                        placeholder="Εισάγετε τον κωδικό σας"
                        value="${requestScope.userRegisterDTO.password}"
                        required
                      />
                    </div>
                    <span class="block text-red-600 text-sm"
                      >${requestScope.passwordMessage}</span
                    >
                  </div>
                  <div class="flex flex-col border-b-2 mb-2 h-[90px]">
                    <label class="text-sm font-sans mb-1" for="confirmPassword"
                      >Επιβεβαίωση Κωδικού</label
                    >
                    <div class="flex items-center gap-x-[10px]">
                      <span class="text-stone-400"
                        ><i class="fa-solid fa-lock"></i
                      ></span>
                      <input
                        class="grow h-10 p-2 text-sm placeholder:font-sans placeholder:text-sm focus:outline-none focus:ring focus:ring-inset-2 focus:ring-[#762124]"
                        id="confirmPassword"
                        type="password"
                        name="confirmPassword"
                        placeholder="Επιβεβαιώστε τον κωδικό σας"
                        value="${requestScope.userRegisterDTO.passwordConfirm}"
                        required
                      />
                    </div>
                    <span class="block text-red-600 text-sm"
                      >${requestScope.confirmPasswordMessage}</span
                    >
                  </div>
                    <div class="flex flex-col border-b-2 h-[90px]">
                        <div class="flex flex-row items-center justify-between">
                      <label class="text-sm font-sans mb-1" for="role">
                      Ρόλος Χρήστη
                      </label>
                      <select id="role" class="w-4/5 rounded-md h-12 bg-white text-sm" name="role">
                          <option value="ADMIN">Διαχειριστής</option>
                          <option value="LIGHT_ADMIN">Βοηθός Διαχειριστή</option>
                      </select>
                      </div>
                    </div>
                  <button
                    type="submit"
                    class="w-full border rounded-xl h-9 text-white text-sm font-thin bg-[#762124] hover:bg-[#5c1a1a] mt-[20px]"
                  >
                    Εγγραφή
                  </button>
                </form>
                <div class="text-center">
                   <span class="text-base pe-2">Επιστροφή στη</span>
                   <a class="text-base text-[#762124] hover:text-[#5c1a1a] hover:underline"
                      href="${pageContext.request.contextPath}/login"
                   >Σύνδεση</a>
                </div>
              </div>
            </main>
    <%@ include file="/WEB-INF/jsp/footer.jsp" %>
  </body>
</html>
