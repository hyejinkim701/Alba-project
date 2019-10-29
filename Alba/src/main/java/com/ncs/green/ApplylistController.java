package com.ncs.green;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import business.AService;
import business.REService;
import vo.ApplylistVO;
import vo.PartimerVO;
import vo.ReservationVO;

@Controller
public class ApplylistController {
   @Autowired
   @Qualifier("applylist")
   private AService aservice;
   
   @Autowired
   @Qualifier("reservation")
   private REService reservice;
   // �̷¼� ���� ���
   @RequestMapping(value="/alist")
   public ModelAndView alist(HttpServletRequest request, ModelAndView mv, ApplylistVO vo) throws ParseException {
      int pno = 0;
      String pid = null;
      String id = null;
      System.out.println("alist Test =>"+vo);
      HttpSession session = request.getSession(false);
      if (session != null) {
         pid = (String)session.getAttribute("ploginID");
         id = (String)session.getAttribute("loginID");            
         ArrayList<ApplylistVO> alist = aservice.selectList();
       
         System.out.println("alist ����Ʈ"+alist);
         LocalDate today = LocalDate.now();
        
         if(pid != null) {
          	 pno = (Integer)session.getAttribute("Pno");
            vo.setPno(pno);
            mv.setViewName("layout2/apply/applyList");
            for(int i=0; i<alist.size(); i++) {
            	String deadline = alist.get(i).getDeadline();
            	LocalDate to = LocalDate.parse(deadline);
            	
            	Period test = Period.between(today, to); 
            
            	 if(test.isNegative()) {
     				alist.get(i).setfCode("BT");
     			} else {
     				alist.get(i).setfCode("AT");
     			} // if
             }	
            mv.addObject("Apple",alist);
          
         } else if(id!=null){
         	 mv.setViewName("layout4/apply/applyList");
         }   
      } else
         System.out.println("**** session �� null ****");
      return mv; 
  } //alist
   
   // ���� ��Ȳ(��ü�� ������ �̷¼� Ȯ��)
   @RequestMapping(value = "/mrlist")
  	public ModelAndView mrlist(HttpServletRequest request, ModelAndView mv, ApplylistVO vo) {
  
  	 ArrayList<ApplylistVO> mrlist = aservice.nselectList();
  	   mv.addObject("Apple", mrlist);
  	   mv.setViewName("layout3/apply/applyListforKing");
  	
		/*
		 * if(mrlist.get(1).getPasscheck().equals("���")){ mv.addObject("fCode", "SS"); }
		 * else { mv.addObject("fCode", "SF"); }
		 */
		 
  			
  	   return mv;
   } //mrlist
   
   
   // �̷¼� �����ϱ�
   @RequestMapping(value="/ainsert")
   @ResponseBody
   public ModelAndView ainsert(HttpServletRequest request,ModelAndView mv, ApplylistVO vo) 
	       throws ServletException, IOException {
	   int kno = 0;
	   int bno = 0;
	   int pno = 0;
	   int cnt2=0;
	   HttpSession session = request.getSession(false);
       if (session != null) {
          kno = (Integer) session.getAttribute("Kno");
          bno = (Integer) session.getAttribute("Bno");
          pno = (Integer) session.getAttribute("Pno");
       } else
          System.out.println("**** session �� null ****");

       vo.setKno(kno);
       vo.setBno(bno);
       vo.setPno(pno);
	   cnt2 = aservice.check_apply(vo);  
	  
	   if(cnt2>0) {
	      mv.addObject("fCode", "AF");
	   } else {
	      int cnt = aservice.insert(vo);
	      if(cnt>0) {
	    	mv.addObject("fCode", "AS");
	      } else {
			mv.addObject("fCode", "AF"); 
	      }
	   }
	   mv.setViewName("jsonView");
	   return mv; 
	} // ainsert
    
    // �̷¼� ���� ���
    @RequestMapping(value="/adelete")
    public ModelAndView adelete(HttpServletRequest request, ModelAndView mv, ApplylistVO vo, PartimerVO vo2) {  
       String pid = null;
       String id=null;
       int cnt = 0;
        
       HttpSession session = request.getSession(false);
       if (session != null) {
          pid = (String) session.getAttribute("ploginID");
          id = (String) session.getAttribute("loginID");
          if(pid != null|| id != null) {
          	 cnt = aservice.delete(vo);
          	 mv.addObject("Apple", vo);
          } else
              System.out.println("**** loginID �� null ****");
       } else
          System.out.println("**** session �� null ****");
     
       if (cnt > 0) {
          mv.setViewName("redirect:/alist");         
       } else { 
          mv.addObject("fCode", "RS");
       }
       return mv;
    } //adelete  
   
    // �հ��� ��ȸ
    @RequestMapping(value = "/resultlist")
  	public ModelAndView resultlist(HttpServletRequest request, ModelAndView mv, ApplylistVO vo) {
    		
       String pid=null;
   
       HttpSession session = request.getSession(false);
       if (session != null) {
    	    pid = (String) session.getAttribute("ploginID");
    	    ArrayList<ApplylistVO> resultlist = aservice.resultList();
    	  	mv.addObject("Apple",resultlist);
       if(pid != null) {
    	    mv.setViewName("layout2/apply/resultList");
       } else
          System.out.println("**** session �� null ****");
     
       } 
       return mv; 
    }
    
    @RequestMapping(value="/selectPass")
    @ResponseBody
    public ModelAndView oinsert(HttpServletRequest request,ModelAndView mv,  ApplylistVO vo2) 
            throws ServletException, IOException {
   	   
   	 	int cnt=0;
   	
   	 	cnt = aservice.selectPass(vo2);
   		
   		  if(cnt>0) {
   		  mv.addObject("fCode", "PS");
   		
   		  } else { mv.addObject("fCode", "PP"); }
   		
   		mv.setViewName("jsonView");
   		return mv; 
    } 
   	    
    // ���հ� ó��
    @RequestMapping(value="/selectFail")
    @ResponseBody
    public ModelAndView selectFail(HttpServletRequest request,ModelAndView mv, ApplylistVO vo) 
            throws ServletException, IOException {
   	   
   	 	int cnt=0;
   	    
   	 	cnt =  aservice.selectFail(vo);
   		
   		  if(cnt>0) {
   		  mv.addObject("fCode", "PF");
   		
   		  } else { mv.addObject("fCode", "PP"); }
   		
   		mv.setViewName("jsonView");
   		return mv; 
    } 
    
}