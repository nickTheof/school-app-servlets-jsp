<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="el">
<head>
  <script src="https://cdn.tailwindcss.com"></script>
  <link
    rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
    integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
    crossorigin="anonymous"
    referrerpolicy="no-referrer"
  />
</head>
<body>
<header>
  <div class="h-[80px] bg-[#762124] flex flex-row justify-between items-center py-2 px-4">
    <div class="flex flex-row space-x-6 items-center justify-start">
      <img
        class="h-[60px] w-auto"
        src="${pageContext.request.contextPath}/img/logo_opa.png"
        alt="opa logo"
      />
      <span class="hidden md:block text-white font-serif font-thin text-xl">
        Coding Factory - Education Reinvented
      </span>
    </div>
    <c:if test="${sessionScope.username != null}">
      <div class="relative z-0 grow flex justify-end">
        <button
          id="logoutMenuToggler"
          aria-expanded="false"
          aria-controls="dropdownHeaderLogoutMenu"
          class="block flex items-center justify-center lg:space-x-4 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-white focus:rounded-md group"
        >
          <span
            class="hidden lg:block text-white font-serif font-thin text-lg underline underline-offset-[6px] group-hover:decoration-2">
            ${sessionScope.username}
          </span>
          <span
            class="text-white text-lg leading-none flex items-center justify-center">
            <i class="fa-solid fa-ellipsis-vertical"></i>
          </span>
        </button>
        <div
          id="dropdownHeaderLogoutMenu"
          class="hidden absolute z-1 top-[50px] right-0 w-[240px] h-16 rounded-md shadow-lg bg-white border transition duration-200 ease-in-out">
          <ul>
            <li>
              <a
                class="block text-[#762124] hover:text-[#5c1a1a] text-lg w-full px-4 py-3 hover:underline focus:outline-none focus:underline"
                href="${pageContext.request.contextPath}/logout">
                Έξοδος
              </a>
            </li>
          </ul>
        </div>
      </div>
    </c:if>
  </div>
</header>
  <script
    type="module"
    src="${pageContext.request.contextPath}/js/header.js"
  ></script>
</body>
</html>