/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.89
 * Generated at: 2024-06-17 02:48:24 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views.member;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class memberEnrollForm_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.LinkedHashSet<>(3);
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<title>Insert title here</title>\r\n");
      out.write("<style>\r\n");
      out.write("   .outer{\r\n");
      out.write("      background:black;\r\n");
      out.write("      color:white;\r\n");
      out.write("      width:1000px;\r\n");
      out.write("      margin:auto;\r\n");
      out.write("      margin-top:50px;\r\n");
      out.write("   }\r\n");
      out.write("   \r\n");
      out.write("   #enroll-form table {margin:auto;}\r\n");
      out.write("   #enroll-form input {margin:5px;}\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("   ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/views/common/header.jsp", out, false);
      out.write("\r\n");
      out.write("   \r\n");
      out.write("   \r\n");
      out.write("   <div class=\"outer\">\r\n");
      out.write("      <br>\r\n");
      out.write("      <h2 align=\"center\">회원가입</h2>\r\n");
      out.write("      \r\n");
      out.write("      <form id=\"enroll-form\" action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contextPath }", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/member/insert\" method=\"post\">\r\n");
      out.write("         <!-- 회원가입form안에.txt -->\r\n");
      out.write("         <table align=\"center\">\r\n");
      out.write("            <tr>\r\n");
      out.write("               <td>* ID</td>\r\n");
      out.write("               <td><input type=\"text\" name=\"userId\" required>\r\n");
      out.write("                  <button type=\"button\" onclick=\"idCheck();\">아이디중복체크</button>\r\n");
      out.write("               </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("               <td>* PWD</td>\r\n");
      out.write("               <td><input type=\"password\" name=\"userPwd\" required></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("               <td>* NAME</td>\r\n");
      out.write("               <td><input type=\"text\" name=\"userName\" required></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("               <td>&nbsp;&nbsp;EMAIL</td>\r\n");
      out.write("               <td><input type=\"email\" name=\"email\"></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("               <td>&nbsp;&nbsp;BIRTHDAY</td>\r\n");
      out.write("               <td><input type=\"text\" name=\"birthday\" placeholder=\"생년월일(6자리)\"></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("               <td>&nbsp;&nbsp;GENDER</td>\r\n");
      out.write("               <td align=\"center\">\r\n");
      out.write("                  <input type=\"radio\" name=\"gender\" value=\"M\" checked> 남\r\n");
      out.write("                  <input type=\"radio\" name=\"gender\" value=\"F\"> 여\r\n");
      out.write("               </td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("               <td>&nbsp;&nbsp;PHONE</td>\r\n");
      out.write("               <td><input type=\"text\" name=\"phone\" placeholder=\"-포함\"></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("            <tr>\r\n");
      out.write("               <td>&nbsp;&nbsp;ADDRESS</td>\r\n");
      out.write("               <td><input type=\"text\" name=\"address\"></td>\r\n");
      out.write("            </tr>\r\n");
      out.write("         </table>\r\n");
      out.write("         <br>\r\n");
      out.write("         <div align=\"center\">\r\n");
      out.write("            <button type=\"reset\">초기화</button>\r\n");
      out.write("            <button type=\"submit\">회원가입</button>\r\n");
      out.write("         </div>\r\n");
      out.write("      </form>\r\n");
      out.write("   </div>\r\n");
      out.write("   \r\n");
      out.write("   <script>\r\n");
      out.write("      function idCheck(){\r\n");
      out.write("         //사용자가 입력한 id값을 가지고 db서버에 이미 존재하는 id인지 확인하는 기능\r\n");
      out.write("         const $userId = $(\"#enroll-form input[name=userId]\");\r\n");
      out.write("         \r\n");
      out.write("         $.ajax({\r\n");
      out.write("            url : \"idCheck\" , // \"/spring/member/idCheck\" - \"\"/spring/member/insert\" 경로 페이지에서 마지막 슬래시 위치에서 \"idCheck\"가 붙음\r\n");
      out.write("            data : {\r\n");
      out.write("               userId : $userId.val()\r\n");
      out.write("            },\r\n");
      out.write("            success : function(result){\r\n");
      out.write("               if(result == 1){ // 이미 사용중인\r\n");
      out.write("                  alert(\"이미 사용중임\");\r\n");
      out.write("                  $userId.val(\"\");\r\n");
      out.write("                  $userId.focus();\r\n");
      out.write("               }else{ // 사용안함\r\n");
      out.write("                  alert(\"사용해도 됩니다.\");\r\n");
      out.write("               }\r\n");
      out.write("            }\r\n");
      out.write("         })\r\n");
      out.write("         \r\n");
      out.write("      }\r\n");
      out.write("   </script>\r\n");
      out.write("   \r\n");
      out.write("\r\n");
      out.write("   ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/views/common/footer.jsp", out, false);
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
