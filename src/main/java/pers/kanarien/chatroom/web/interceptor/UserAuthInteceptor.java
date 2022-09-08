package pers.kanarien.chatroom.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import pers.kanarien.chatroom.model.vo.ResponseJson;
import pers.kanarien.chatroom.util.Constant;
import pers.kanarien.chatroom.util.JsonMsgHelper;

public class UserAuthInteceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        Object userToken = session.getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
//            JsonMsgHelper.writeJson(response, new ResponseJson(HttpStatus.FORBIDDEN).setMsg("请登录"),
//                    HttpStatus.FORBIDDEN);
            response.sendRedirect("login");
            return false;
        }
        return true;
    }

//    called after processing the request but before generating the view.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        // when the Request.credentials is “include” mode browsers will expose the response
        // to front-end JavaScript code if the Access-Control-Allow-Credentials is set true.
        response.setHeader("Access-Control-Allow-Credentials","true");     
    }

//    called after the complete request is finished and the view is generated.
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        
    }

}
