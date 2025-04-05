<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>

<!DOCTYPE html>
<html lang="en">
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
                  <span class="block">Είσοδος</span>
                  <span class="block text-red-600 text-lg"
                    >${error}</span
                  >
                </div>
                <form method="POST" action="${pageContext.request.contextPath}/login" class="p-4">
                  <div class="flex flex-col border-b-2 mb-2">
                    <label class="text-sm font-sans mb-1" for="username">Email</label>
                    <div class="flex items-center gap-x-[10px] mb-[10px]">
                      <span class="text-stone-400"
                        ><i class="fa-solid fa-user"></i
                      ></span>
                      <input
                        class="grow h-10 p-2 text-sm placeholder:font-sans placeholder:text-sm focus:outline-none focus:ring focus:ring-inset-2 focus:ring-[#762124]"
                        id="username"
                        name="username"
                        type="email"
                        placeholder="Εισάγετε το email σας"
                        required
                      />
                    </div>
                  </div>
                  <div class="flex flex-col border-b-2">
                    <label class="text-sm font-sans mb-1" for="password"
                      >Password</label
                    >
                    <div class="flex items-center gap-x-[10px] mb-[10px]">
                      <span class="text-stone-400"
                        ><i class="fa-solid fa-lock"></i
                      ></span>
                      <input
                        class="grow h-10 p-2 text-sm placeholder:font-sans placeholder:text-sm focus:outline-none focus:ring focus:ring-inset-2 focus:ring-[#762124]"
                        id="password"
                        type="password"
                        name="password"
                        placeholder="Εισάγετε τον κωδικό σας"
                        required
                      />
                    </div>
                  </div>
                  <div class="flex justify-end items-center mt-[10px] mb-[20px]">
                    <a
                      class="text-sm text-stone-600 hover:text-stone-800 hover:underline hover:decoration-solid"
                      href="#"
                      >Ξεχάσατε τον κωδικό σας;</a
                    >
                  </div>
                  <button
                    type="submit"
                    class="w-full border rounded-xl h-9 text-white text-sm font-thin bg-[#762124] hover:bg-[#5c1a1a]"
                  >
                    Είσοδος
                  </button>
                </form>
                <div class="text-center mt-4">
                  <span class="pe-2">Δεν έχετε λογαριασμό;</span
                  ><a
                    class="text-[#762124] hover:text-[#5c1a1a] hover:underline"
                    href="${pageContext.request.contextPath}/register"
                    >Εγγραφή</a
                  >
                </div>
              </div>
            </main>
    <%@ include file="/WEB-INF/jsp/footer.jsp" %>
  </body>
</html>
