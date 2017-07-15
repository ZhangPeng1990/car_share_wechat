package car.top.content360.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public abstract class BaseController {
	protected static Log log = null;
	protected final String TEMP_DIR = "/temp";
	
	public BaseController() {
		log = LogFactory.getLog(this.getClass());
	}


	protected ModelAndView ajaxDone(int statusCode, String message, String forwardUrl) {
		ModelAndView mav = new ModelAndView("ajaxDone");
		mav.addObject("statusCode", statusCode);
		mav.addObject("message", message);
		mav.addObject("forwardUrl", forwardUrl);
		return mav;
	}
	
	protected ModelAndView ajaxDoneSuccess(String message) {
		return ajaxDone(200, message, "");
	}

	protected ModelAndView ajaxDoneError(String message) {
		return ajaxDone(300, message, "");
	}

	protected void download(HttpServletResponse response, InputStream is){
		ServletOutputStream os = null;
		try {

			os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int n = -1;
			while ((n = is.read(buffer, 0, buffer.length)) != -1) {
				os.write(buffer, 0, n);
			}

			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				is = null;
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				os = null;
			}
		}
	}
	
	protected void download(HttpServletResponse response, File file){
		System.out.println("physicalPath=" + file.getAbsolutePath());
		try {
			download(response,new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("download Error=" + file.getAbsolutePath());
		}
	}
	
	protected void download(HttpServletResponse response, String physicalPath){
		System.out.println("physicalPath=" + physicalPath);
		try {
			download(response,new FileInputStream(new File(physicalPath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("download Error=" + physicalPath);
		}
	}
	
	protected Date parseDate(String source) throws ParseException {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.parse(source);
	}


	protected String getMessage(String code) {
		return this.getMessage(code, new Object[] {});
	}

	protected String getMessage(String code, Object arg0) {
		return getMessage(code, new Object[] { arg0 });
	}

	protected String getMessage(String code, Object arg0, Object arg1) {
		return getMessage(code, new Object[] { arg0, arg1 });
	}

	protected String getMessage(String code, Object arg0, Object arg1,
			Object arg2) {
		return getMessage(code, new Object[] { arg0, arg1, arg2 });
	}

	protected String getMessage(String code, Object arg0, Object arg1,
			Object arg2, Object arg3) {
		return getMessage(code, new Object[] { arg0, arg1, arg2, arg3 });
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView exception(Exception e, HttpServletRequest request) {
		e.printStackTrace();
		
		request.setAttribute("exception", e);
		
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("statusCode", 300);
		mav.addObject("message", e.getMessage());
		
		return mav;
	}

}