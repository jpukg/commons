package com.samenea.common.security.web.taglibs;

import com.samenea.common.security.service.CaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: alireza
 * Date: Nov 14, 2010
 * Time: 11:24:11 AM
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class CaptchaTag extends SimpleTagSupport {
    Logger logger = LoggerFactory.getLogger(CaptchaTag.class);
    private static final String DEFAULT_CAPTCHA_INPUT_NAME = "j_captcha";
    private String reloadImageSrc;
    private String captchaImageSrc;
    private String jqueryCaptchaCssUrl;
    private String jqueryCaptchaUrl;
    private String id;
    private String name;
    @Autowired
    private CaptchaService captchaService;

    public void setReloadImageSrc(String reloadImageSrc) {
        this.reloadImageSrc = reloadImageSrc;
    }


    public void setCaptchaImageSrc(String captchaImageSrc) {
        this.captchaImageSrc = captchaImageSrc;
    }

    public void setJqueryCaptchaCssUrl(String jQueryCaptchaCssUrl) {
        this.jqueryCaptchaCssUrl = jQueryCaptchaCssUrl;
    }

    public void setJqueryCaptchaUrl(String jQueryCaptchaUrl) {
        this.jqueryCaptchaUrl = jQueryCaptchaUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void doTag() throws JspException, IOException {
        PageContext context = (PageContext) getJspContext();
        HttpServletRequest request =
                (HttpServletRequest) context.getRequest();

        boolean render = captchaService.hasCaptchaRendered(request.getRequestedSessionId());
        if(!render){
            return;
        }
        JspWriter out = getJspContext().getOut();
        try {
            out.write("<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"" + jqueryCaptchaCssUrl + "\"/>");
            out.write("<script type=\"text/javascript\" src=\"" + jqueryCaptchaUrl + "\"></script>");
            String inputName=name != null ? name : DEFAULT_CAPTCHA_INPUT_NAME;
            String script = "<script type=\"text/javascript\">\n" +
                    "\n" +
                    " $(document).ready(function () {" +
                    "$('#captchaContainer').captcha({\n" +
                    "            captchaImageSrc:'" + captchaImageSrc + "',\n" +
                    "            reloadImageSrc:'" + reloadImageSrc + "',\n" +
                    "            captchaInputName:'" + inputName + "'\n" +
                    "        },function(  ) {\n" +
                    "            console.log(\"DONE\")\n" +
                    "        });" +
                    "\n" +
                    "  });" +
                    "\n" +
                    "</script>";
            out.write(script);
            out.write("<div id='" + id + "' />");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}

