//package com.capstone.precare.controller;
//
//import java.io.File;
//import java.text.DateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.swing.JFileChooser;
//import javax.swing.JOptionPane;
//
//import org.apache.ibatis.session.SqlSession;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.wherever.precareweb.dao.PrecareDao;
//import com.wherever.precareweb.dto.Prediction;
//import com.wherever.precareweb.dto.ProgressViewDto;
//import com.wherever.precareweb.dto.QuestionDto;
//import com.wherever.precareweb.dto.ResultDto;
//import com.wherever.precareweb.dto.UserDto;
//import com.wherever.precareweb.emailService.Email;
//
//
//
///**
// * Handles requests for the application home page.
// */
////@EnableAutoConfiguration
//@Controller
//public class HomeController {
//
//	@Autowired
//	public SqlSession sqlSession;
//
//	@Autowired
//	BCryptPasswordEncoder pwdEncoder;
//
////	@Autowired
////	public EmailSender emailSender;
//
//	@Autowired
//	public Email email;
//
//
//
//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
//
//	/**
//	 * Simply selects the home view to render by returning its name.
//	 */
//	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
//	public String home(Locale locale, Model model, HttpServletRequest request) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		UserDto userInfo = null;
//		ProgressViewDto progressInfo = null;
//		ArrayList<QuestionDto> questionList = null;
//		HashMap queEndNum = null;
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//
//		String formattedDate = dateFormat.format(date);
//
//		model.addAttribute("serverTime", formattedDate );
//
//		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//
//		String user_id= SecurityContextHolder.getContext().getAuthentication().getName().toString();
//
//		if("anonymousUser".equals(user_id) || "".equals(user_id)|| user_id == null) {
//			//�α����� �ȵ��ִ� ���
//			model.addAttribute("user_name", "Guest");
//		}else {
//			//�α����� �Ǿ��ִ� ���
//			model.addAttribute("user_name", dao.selectUserNameWithIdDao(user_id));
//			userInfo = dao.selectAllUserWithIdDao(user_id);
//			progressInfo = dao.selectAllProgressWithIdDao(user_id);
//			queEndNum = dao.selectQuestionEndNumDao();
//		}
//
//		// ���� -> �α����� �ȵǾ� ���� ��쿡�� ��� �� ���ΰ�?
//
//		/*
//		 * �������� ���� �˰���
//		 * 1. �������� ������ �����´�. (1:auto/2:�����/3:��Ȳ���/4:�������/5:�������/6:���ͳ��ߵ�) -> �⺻ ������ auto
//		      - auto�� �� �׸� �� ���� 1���� ������
//		 * 2. progress ���̺��� �����Ͽ� ���� ����� �������� ��ȣ�� �� �׸񺰷� �����´�.
//		 *    - ��, �ش� ������ ��ȣ�� ������ ��ȣ�� ���(- �� Ǭ �����, % �̿�)
//		        1. ù���� ������ ����
//		 * 3. 1������ ���õ� �׸����� 5���� ������ �����´�.
//		 * 4. ������ veiw ������ �ѷ��ش�.
//		 */
//		String queMode = "";
//		String nextDepressionId = "";
//		String nextPanicdisorderId = "";
//		String nextPersonalitydisorderId = "";
//		String nextInsomniaId = "";
//		String nextInternetaddictionId = "";
//
//		//1. �ֱٿ� ����� ������ �����´�. -> progressInfo
//		//2. �� �׸� �� ���� ���� ��ȣ�� ���Ѵ�.
//
//		if("anonymousUser".equals(user_id) || "".equals(user_id)|| user_id == null) {
//			//�α����� �ȵ��ִ� ���
//			//�������� ��� ����
//			queMode = "auto";
//			nextDepressionId = "001001";
//			nextPanicdisorderId = "002001";
//			nextPersonalitydisorderId = "003001";
//			nextInsomniaId = "004001";
//			nextInternetaddictionId = "005001";
//		}else {
//			//�α����� �Ǿ��ִ� ���
//			//�׷����� ���ؼ� �� ���� �� ���� ��Ȳ ����
//			//�� �����Ȳ �ʱ�ȭ
//			model.addAttribute("status_dep", ((progressInfo.getPro_depressionState())!=null)?(Integer.parseInt(progressInfo.getPro_depressionState().substring(3, 6)) ):0);
//			model.addAttribute("status_pan", ((progressInfo.getPro_panicdisorderState())!=null)?(Integer.parseInt(progressInfo.getPro_panicdisorderState().substring(3, 6)) ):0);
//			model.addAttribute("status_per", ((progressInfo.getPro_personalitydisorderState())!=null)?(Integer.parseInt(progressInfo.getPro_personalitydisorderState().substring(3, 6)) ):0);
//			model.addAttribute("status_ins", ((progressInfo.getPro_dyslepsiaState())!=null)?(Integer.parseInt(progressInfo.getPro_dyslepsiaState().substring(3, 6)) ):0);
//			model.addAttribute("status_int", ((progressInfo.getPro_internetaddictionState())!=null)?(Integer.parseInt(progressInfo.getPro_internetaddictionState().substring(3, 6)) ):0);
//
//
//			//�������� ��� ����
//			queMode = userInfo.getUser_questionMode();
//			//�����
//			int temp = ((progressInfo.getPro_depressionState())!=null)?(Integer.parseInt(progressInfo.getPro_depressionState().substring(3, 6)) ):0;
//
//			model.addAttribute("status_dep", String.valueOf(((float)temp/(float)Integer.parseInt(queEndNum.get("qen_depressionENum").toString()))*100));
//			String strTemp = "";
//			if(temp % Integer.parseInt(queEndNum.get("qen_depressionENum").toString()) == 0) {
//				//������ �� Ǭ ���
//				strTemp = "001";
//			}else {
//				//������ �� Ǯ�� ���� ��� -> temp ���ڸ� 3�ڸ��� ���ڿ��� ����
//				strTemp = String.format("%03d", temp+1);
//			}
//			nextDepressionId = "001" + strTemp;
//
//			//��Ȳ���
//			temp = ((progressInfo.getPro_panicdisorderState())!=null)?(Integer.parseInt(progressInfo.getPro_panicdisorderState().substring(3, 6)) ):0;
//			model.addAttribute("status_pan", String.valueOf(((float)temp/(float)Integer.parseInt(queEndNum.get("qen_panicENum").toString()))*100));
//			strTemp = "";
//			if(temp % Integer.parseInt(queEndNum.get("qen_panicENum").toString()) == 0) {
//				//������ �� Ǭ ���
//				strTemp = "001";
//			}else {
//				//������ �� Ǯ�� ���� ��� -> temp ���ڸ� 3�ڸ��� ���ڿ��� ����
//				strTemp = String.format("%03d", temp+1);
//			}
//			nextPanicdisorderId = "002" + strTemp;
//
//			//�������
//			temp = ((progressInfo.getPro_personalitydisorderState())!=null)?(Integer.parseInt(progressInfo.getPro_personalitydisorderState().substring(3, 6)) ):0;
//			model.addAttribute("status_per", String.valueOf(((float)temp/(float)Integer.parseInt(queEndNum.get("qen_personalityENum").toString()))*100));
//			strTemp = "";
//			if(temp % Integer.parseInt(queEndNum.get("qen_personalityENum").toString()) == 0) {
//				//������ �� Ǭ ���
//				strTemp = "001";
//			}else {
//				//������ �� Ǯ�� ���� ��� -> temp ���ڸ� 3�ڸ��� ���ڿ��� ����
//				strTemp = String.format("%03d", temp+1);
//			}
//			nextPersonalitydisorderId = "003" + strTemp;
//
//			//�������
//			temp = ((progressInfo.getPro_dyslepsiaState())!=null)?(Integer.parseInt(progressInfo.getPro_dyslepsiaState().substring(3, 6)) ):0;
//			model.addAttribute("status_ins", String.valueOf(((float)temp/(float)Integer.parseInt(queEndNum.get("qen_dyslepsiaENum").toString()))*100));
//			strTemp = "";
//			if(temp % Integer.parseInt(queEndNum.get("qen_dyslepsiaENum").toString()) == 0) {
//				//������ �� Ǭ ���
//				strTemp = "001";
//			}else {
//				//������ �� Ǯ�� ���� ��� -> temp ���ڸ� 3�ڸ��� ���ڿ��� ����
//				strTemp = String.format("%03d", temp+1);
//			}
//			nextInsomniaId = "004" + strTemp;
//
//			//���ͳ��ߵ�
//			temp = ((progressInfo.getPro_internetaddictionState())!=null)?(Integer.parseInt(progressInfo.getPro_internetaddictionState().substring(3, 6)) ):0;
//			model.addAttribute("status_int", String.valueOf(((float)temp/(float)Integer.parseInt(queEndNum.get("qen_internetENum").toString()))*100));
//			strTemp = "";
//			if(temp % Integer.parseInt(queEndNum.get("qen_internetENum").toString()) == 0) {
//				//������ �� Ǭ ���
//				strTemp = "001";
//			}else {
//				//������ �� Ǯ�� ���� ��� -> temp ���ڸ� 3�ڸ��� ���ڿ��� ����
//				strTemp = String.format("%03d", temp+1);
//			}
//			nextInternetaddictionId = "005" + strTemp;
//		}
//
//
//		if("auto".equals(queMode)) {
//			//��ü ������ �����´�.
//			questionList = dao.selectAllQuestionDao();
//			//�� ���� �ϳ����� �����´�.
//			for(QuestionDto que : questionList) {
//				if("depression".equals(que.getQue_sort())) {
//					//1. ����� ���� ���� �� ����
//					if(nextDepressionId.equals(que.getQue_id())) {
//						model.addAttribute("question1", que.getQue_text());
//						model.addAttribute("id_question1", que.getQue_id());
//						continue;
//					}
//				}else if("Panic disorder".equals(que.getQue_sort())) {
//					//2. ��Ȳ��� ���� ���� �� ����
//					if(nextPanicdisorderId.equals(que.getQue_id())) {
//						model.addAttribute("question2", que.getQue_text());
//						model.addAttribute("id_question2", que.getQue_id());
//						continue;
//					}
//				}else if("Personality disorder".equals(que.getQue_sort())) {
//					//3. ������� ���� ���� �� ����
//					if(nextPersonalitydisorderId.equals(que.getQue_id())) {
//						model.addAttribute("question3", que.getQue_text());
//						model.addAttribute("id_question3", que.getQue_id());
//						continue;
//					}
//				}else if("Imsomnia".equals(que.getQue_sort())) {
//					//4. ������� ���� ���� �� ����
//					if(nextInsomniaId.equals(que.getQue_id())) {
//						model.addAttribute("question4", que.getQue_text());
//						model.addAttribute("id_question4", que.getQue_id());
//						continue;
//					}
//				}else if("Internet addiction".equals(que.getQue_sort())) {
//					//5. ���ͳ��ߵ� ���� ���� �� ����
//					if(nextInternetaddictionId.equals(que.getQue_id())) {
//						model.addAttribute("question5", que.getQue_text());
//						model.addAttribute("id_question5", que.getQue_id());
//						continue;
//					}
//				}
//			}
//		}
//
//		return "home";
//	}
//
//	@RequestMapping("/loginForm")
//	public String loginForm(Locale locale, Model model) throws Exception {
//		//use this under code when you use db.
//		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//
//		return "login/loginForm";
//	}
//
//	@RequestMapping("/loginSuccess")
//	public String loginSuccess(Locale locale, Model model) throws Exception {
//		//use this under code when you use db.
//		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//
//		return "login/loginSuccess";
//	}
//
//	@RequestMapping("/showResult")
//	public String showResult(Locale locale, Model model, HttpServletRequest request) throws Exception {
//		String page = "list/resultPage";
//		Boolean checkResultToCome = false;
//		Prediction preWithIdContent = null;
//		String managers = null;
//		String[] argManagers = null;
//		String loginId = SecurityContextHolder.getContext().getAuthentication().getName().toString();
//		//use this under code when you use db.
//		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//		String user_id= request.getParameter("user_id");
//		String target_preId = request.getParameter("target_preId");
//
//		//�����ϰ��� �ϴ� ����ڰ� ���� �ڽ��� ���
//		if(user_id == null || "".equals(user_id) || "anonymousUser".equals(user_id)) {
//			//get current login user's infomation
//			user_id= loginId;
//			checkResultToCome = true;
//		}
//		//�����ϰ��� �ϴ� ����ڰ� �����ϴ��� Ȯ��
//		String userName = dao.selectUserNameWithIdDao(user_id);
//		if(userName == null || "".equals(userName)) {
//			page = "cmmn/notFoundUser";
//			return page;
//		}
//
//		//�����ϰ��� �ϴ� ����ڰ� �ڽ��̶�� ��ȸ ����
//		if(loginId.equals(user_id))
//			checkResultToCome = true;
//
//		// �����ϰ��� �ϴ� ����ڰ� ��ȸ�Ϸ��� ������� �����ڰ� �´��� Ȯ��
//		if(!checkResultToCome){
//			managers = dao.selectAllManagersWithIdDao(user_id);
//			argManagers = managers.split(",");
//			for(String word : argManagers) {
//				if(loginId.equals(word))
//					checkResultToCome = true;
//			}
//			if(!checkResultToCome) {
//				page = "cmmn/notAccessUser";
//				return page;
//			}
//
//		}
//
//
//		model.addAttribute("user_id", user_id);
//		model.addAttribute("user_name", userName);
//		model.addAttribute("login_id", loginId);
//
//		//prediction ���� ��������
//		int numPrediction = dao.selectCountPredictionWithIdDao(user_id);
//		List<Prediction> predictionList = null;
//		if(numPrediction > 0) {
//			predictionList = dao.selectAllPredictionWithIdDao(user_id);
//		}
//		model.addAttribute("prediction_count", numPrediction);
//		model.addAttribute("prediction_list", predictionList);
//
//		if(target_preId != null) {
//			preWithIdContent = dao.selectPredictionWithIdDao(target_preId);
//		}
//		else {
//			if(numPrediction > 0)
//				preWithIdContent = (Prediction)predictionList.get(0);
//		}
//
//		model.addAttribute("target_prediction", preWithIdContent);
//
//
//		return page;
//	}
//
//
//	@RequestMapping("/updateComment")
//	public String updateComment(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String resultPage = "cmmn/saveCommentSuccess";
//		try {
//		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//		request.setCharacterEncoding("UTF-8");
//		String target_userId = request.getParameter("target_userId");
//		String user_id = SecurityContextHolder.getContext().getAuthentication().getName().toString();
//		String user_name = dao.selectUserNameWithIdDao(user_id);
//		String pre_comment = request.getParameter("pre_comment");
//		String pre_id = request.getParameter("pre_id");
//		//�Է¹��� comment�� �� �� ����ڿ� �ð��� �� ����
//		//pre_comment = user_id+"("+user_name+") : "+ pre_comment;
//
//		Map<String, String> pre_map = new HashMap<String, String>();
//		pre_map.put("pre_id", pre_id);
//		pre_map.put("pre_comment", pre_comment);
//		dao.updateCommentWithIdDao(pre_map);
//		model.addAttribute("target_userId", target_userId);
//
//		}catch(Exception ex) {
//			resultPage = "cmmn/saveDataFailure";
//			System.out.println(ex.getStackTrace());
//		}
//		return resultPage;
//	}
//
//
//	@RequestMapping("/checkForm")
//	public String checkForm(Locale locale, Model model, HttpServletRequest request) throws Exception {
//		String resultPage = "cmmn/saveDataSuccess";
//		String user_id= SecurityContextHolder.getContext().getAuthentication().getName().toString();
//		UserDto userInfo = null;
//		ResultDto resDto = null;
//		HashMap<String, Integer> endNumQuestion = null;
//		ArrayList<String> ansList = null;
//		PredictorMaker pm = null;
//		HashMap<String, Object> userData = new HashMap<String, Object>();
//		try {
//		PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//
//		//�α��� ���� Ȯ��
//		if("anonymousUser".equals(user_id) || "".equals(user_id)|| user_id == null) {
//			//�α����� �ȵ��ִ� ���
//			resultPage = "login/loginForm";
//			return resultPage;
//		}else {
//			//�α����� �Ǿ��ִ� ���
//			//db�� ������ �����ϱ�
//			pm = new PredictorMaker();
//			userInfo = dao.selectAllUserWithIdDao(user_id);
//			//�� ������ ������ ��ȣ
//			endNumQuestion = dao.selectQuestionEndNumDao();
//			//����� ���� ����
//			userData.put("age", Integer.valueOf(userInfo.getUser_age()));
//			userData.put("sex", userInfo.getUser_sex());
//			userData.put("occupation", userInfo.getUser_occupation());
//			//��ȣ�� ���� ����
//			String managers = dao.selectAllManagersWithIdDao(user_id);
//			String[] argManagers = null;
//			if(managers == null || "".equals(managers))
//				argManagers = null;
//			else
//				argManagers = managers.split(",");
//			/*
//			 * �������� ���� �˰���
//			 * 1. �������� ����� ������ �޾ƿ´�.
//			 * 2. ����� ������ db�� �ϳ��� �����Ѵ�.
//			      - res_id : �ڵ�����
//			        res_userId : �α��ε� ����� id ����
//			        res_questionId : ���� id�� �޾Ƽ� ����
//			        res_questionAnswer : ������ �´� ��� ����
//			        res_status : ���� ����(ex 001)�� �̿��ؼ� 'progress' db�� ���� count+1 �� ����
//			        res_date : �ڵ�����
//			 * 3. ����, �ش��׸��� �������縦 ��ģ ���(������ȣ�� �ش��׸��� �������� ���, %�̿�)
//			        1. user_id�� ���� ����� last_status�� �̿��ؼ� ������ 'predictor' Ŭ������ ������.
//			        2. ���� ����� Ȯ���� �޾ƿͼ� 'prediction' db�� ������ �����Ѵ�.
//
//			 */
//
//			//1
//			int question1 = Integer.parseInt(request.getParameter("question1"));
//			String id_question1 = request.getParameter("id_question1");
//			int question2 = Integer.parseInt(request.getParameter("question2"));
//			String id_question2 = request.getParameter("id_question2");
//			int question3 = Integer.parseInt(request.getParameter("question3"));
//			String id_question3 = request.getParameter("id_question3");
//			int question4 = Integer.parseInt(request.getParameter("question4"));
//			String id_question4 = request.getParameter("id_question4");
//			int question5 = Integer.parseInt(request.getParameter("question5"));
//			String id_question5 = request.getParameter("id_question5");
//
//			//2
//			Map idSortMap = new HashMap();
//			idSortMap.put("pre_userId", user_id);
//			Map tempMap = new HashMap();
//			tempMap.put("res_userId", user_id);
//
//			int depStatus = 0;
//			int panStatus = 0;
//			int perStatus = 0;
//			int dysStatus = 0;
//			int intStatus = 0;
//			//���� ���� ���
//			//int sortNum = Integer.parseInt(request.getParameter("sort"));
//			int sortNum = 0;
//			if(sortNum == 0) {
//				//auto�� ���
//				//ù ��° ����
//				idSortMap.put("pre_sort", "depression");
//				depStatus = dao.countStateWithIdAndSortDao(idSortMap)+1;
//				resDto = new ResultDto(user_id, id_question1, question1, depStatus);
//				dao.insertSurveyAnswerDao(resDto);
//				//�� ��° ����
//				idSortMap.replace("pre_sort", "Panic disorder");
//				panStatus = dao.countStateWithIdAndSortDao(idSortMap)+1;
//				resDto = new ResultDto(user_id, id_question2, question2, panStatus);
//				dao.insertSurveyAnswerDao(resDto);
//				//�� ��° ����
//				idSortMap.replace("pre_sort", "Personality disorder");
//				perStatus = dao.countStateWithIdAndSortDao(idSortMap)+1;
//				resDto = new ResultDto(user_id, id_question3, question3, perStatus);
//				dao.insertSurveyAnswerDao(resDto);
//				//�� ��° ����
//				idSortMap.replace("pre_sort", "Imsomnia");
//				dysStatus = dao.countStateWithIdAndSortDao(idSortMap)+1;
//				resDto = new ResultDto(user_id, id_question4, question4, dysStatus);
//				dao.insertSurveyAnswerDao(resDto);
//				//�ټ� ��° ����
//				idSortMap.replace("pre_sort", "Internet addiction");
//				intStatus = dao.countStateWithIdAndSortDao(idSortMap)+1;
//				resDto = new ResultDto(user_id, id_question5, question5, intStatus);
//				dao.insertSurveyAnswerDao(resDto);
//				//3. �˻��ϱ� (�� ������ ������ ����ģ ���)
//
//				if(String.valueOf(endNumQuestion.get("qen_depressionENum")).equals(String.valueOf(Integer.parseInt(id_question1.substring(3, 6))))) {
//					//����� �˻縦 ��ģ ��� -> ��������� �޾Ƽ� db�� ������
//					tempMap.put("res_questionId", "001%");
//					tempMap.put("res_status", depStatus);
//					pm.depressionPredictor(dao.selectAnswerDao(tempMap), userData);
//					//����� ��� db�� �����Ű��
//					Map preMap2 = new HashMap();
//					preMap2.put("pre_userId", user_id);
//					preMap2.put("pre_sort", "depression");
//					preMap2.put("pre_result", pm.getF_result());
//					preMap2.put("pre_probability", pm.getF_posibility());
//					dao.insertPredictDao(preMap2);
//					//����� ��� ��ȣ�� �̸��Ϸ� �����ϱ�
//					try {
//						sendEmailToManager(userInfo, argManagers, "�����", pm);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						System.out.println("Email ������ ��ɿ� ������ �߻��߽��ϴ�.");
//						e.printStackTrace();
//					}
//				} if(String.valueOf(endNumQuestion.get("qen_panicENum")).equals(String.valueOf(Integer.parseInt(id_question2.substring(3, 6))))) {
//					//��Ȳ��� ���� ���
//					tempMap.put("res_questionId", "002%");
//					tempMap.put("res_status", panStatus);
//					pm.panicDisorderPredictor(dao.selectAnswerDao(tempMap), userData); //�����ؾ� ��
//					//��Ȳ��� ��� db�� �����Ű��
//					Map preMap2 = new HashMap();
//					preMap2.put("pre_userId", user_id);
//					preMap2.put("pre_sort", "Panic disorder");
//					preMap2.put("pre_result", pm.getF_result());
//					preMap2.put("pre_probability", pm.getF_posibility());
//					dao.insertPredictDao(preMap2);
//					//��Ȳ��� ��� ��ȣ�� �̸��Ϸ� �����ϱ�
//					try {
//						sendEmailToManager(userInfo, argManagers, "��Ȳ���", pm);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						System.out.println("Email ������ ��ɿ� ������ �߻��߽��ϴ�.");
//						e.printStackTrace();
//					}
//				} if(String.valueOf(endNumQuestion.get("qen_personalityENum")).equals(String.valueOf(Integer.parseInt(id_question3.substring(3, 6))))) {
//					//������� ���� ���
//					tempMap.put("res_questionId", "003%");
//					tempMap.put("res_status", perStatus);
//					pm.personalityDisorderPredictor(dao.selectAnswerDao(tempMap), userData); //�����ؾ� ��
//					//������� ��� db�� �����Ű��
//					Map preMap2 = new HashMap();
//					preMap2.put("pre_userId", user_id);
//					preMap2.put("pre_sort", "Personality disorder");
//					preMap2.put("pre_result", pm.getF_result());
//					preMap2.put("pre_probability", pm.getF_posibility());
//					dao.insertPredictDao(preMap2);
//					//������� ��� ��ȣ�� �̸��Ϸ� �����ϱ�
//					try {
//						sendEmailToManager(userInfo, argManagers, "�������", pm);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						System.out.println("Email ������ ��ɿ� ������ �߻��߽��ϴ�.");
//						e.printStackTrace();
//					}
//				} if(String.valueOf(endNumQuestion.get("qen_dyslepsiaENum")).equals(String.valueOf(Integer.parseInt(id_question4.substring(3, 6))))) {
//					//������� ���� ���
//					tempMap.put("res_questionId", "004%");
//					tempMap.put("res_status", dysStatus);
//					pm.imsomniaDisorderPredictor(dao.selectAnswerDao(tempMap), userData); //�����ؾ� ��
//					//������� ��� db�� �����Ű��
//					Map preMap2 = new HashMap();
//					preMap2.put("pre_userId", user_id);
//					preMap2.put("pre_sort", "Imsomnia");
//					preMap2.put("pre_result", pm.getF_result());
//					preMap2.put("pre_probability", pm.getF_posibility());
//					dao.insertPredictDao(preMap2);
//					//������� ��� ��ȣ�� �̸��Ϸ� �����ϱ�
//					try {
//						sendEmailToManager(userInfo, argManagers, "�������", pm);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						System.out.println("Email ������ ��ɿ� ������ �߻��߽��ϴ�.");
//						e.printStackTrace();
//					}
//				} if(String.valueOf(endNumQuestion.get("qen_internetENum")).equals(String.valueOf(Integer.parseInt(id_question5.substring(3, 6))))) {
//					//���ͳ��ߵ� ���� ���
//					tempMap.put("res_questionId", "005%");
//					tempMap.put("res_status", intStatus);
//					pm.internetAddictionPredictor(dao.selectAnswerDao(tempMap), userData); //�����ؾ� ��
//					//���ͳ��ߵ� ��� db�� �����Ű��
//					Map preMap2 = new HashMap();
//					preMap2.put("pre_userId", user_id);
//					preMap2.put("pre_sort", "Internet addiction");
//					preMap2.put("pre_result", pm.getF_result());
//					preMap2.put("pre_probability", pm.getF_posibility());
//					dao.insertPredictDao(preMap2);
//					//���ͳ��ߵ� ��� ��ȣ�� �̸��Ϸ� �����ϱ�
//					try {
//						sendEmailToManager(userInfo, argManagers, "���ͳ��ߵ�", pm);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						System.out.println("Email ������ ��ɿ� ������ �߻��߽��ϴ�.");
//						e.printStackTrace();
//					}
//				}
//
//			}
//
//		}
//
//		}catch(Exception ex) {
//			resultPage = "cmmn/saveDataFailure";
//			System.out.println(ex.getMessage());
//			System.out.println(ex.getCause());
//			System.out.println(ex.getLocalizedMessage());
//		}
//		return resultPage;
//	}
//
//
//	@RequestMapping("/PredictorMaker")
//	public String PredictorMaker(Locale locale, Model model, HttpServletRequest request) throws Exception {
//		String resultPage = "content1";
//		try {
//			PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//			int[] temp = {3,4,3,3,4,2,5,5,3,4,4,3,5,1,5};
//			ArrayList<Integer> temp2 = new ArrayList<Integer>(
//					Arrays.asList(3,4,3,3,4,2,5,5,3,4,4,3,5,1,5));
//
//			HashMap<String, Object> userData = new HashMap<String, Object>();
//			userData.put("age", 24);
//			userData.put("sex", "male");
//			userData.put("occupation", "yes");
//
//			PredictorMaker pm = new PredictorMaker();
//			pm.depressionPredictor(temp2, userData);
//			//System.out.println("��� : " + pm.getF_result());
//			//System.out.println("Ȯ�� : " + pm.getF_posibility());
//
//			Map dd = new HashMap();
//			dd.put("res_userId", "limjihun204");
//			dd.put("res_questionId", "001%");
//			dd.put("res_status", "1");
//			//System.out.println("test : \n" + dao.selectAnswerDao(dd));
//
//		}catch(Exception ex) {
//			resultPage = "cmmn/saveDataFailure";
//			System.out.println(ex.getStackTrace());
//		}
//		return resultPage;
//	}
//
//
//	@RequestMapping("/signUpForm")
//	public String signUpForm(Locale locale, Model model, HttpServletRequest request) throws Exception {
//		String resultPage = "login/signUpForm";
//		return resultPage;
//	}
//
//	@RequestMapping("/settingForm")
//	public String settingForm(Locale locale, Model model, HttpServletRequest request) throws Exception {
//		String resultPage = "setting/settingForm";
//		String user_id = "";
//		List<UserDto> manager_list = new ArrayList<UserDto>();
//		try {
//			user_id = SecurityContextHolder.getContext().getAuthentication().getName().toString();
//			PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//			if("anonymousUser".equals(user_id) || "".equals(user_id)|| user_id == null) {
//				//�α����� �ȵ��ִ� ���
//				manager_list = null;
//				model.addAttribute("manager_list", manager_list);
//			}else {
//				//�α����� �Ǿ��ִ� ���
//				UserDto userInfo = dao.selectAllUserWithIdDao(SecurityContextHolder.getContext().getAuthentication().getName().toString());
//				String[] arrManager = userInfo.getUser_manager().split(",");
//				for(String data : arrManager) {
//					if(!"".equals(data))
//						manager_list.add(dao.selectAllUserWithIdDao(data));
//				}
//				model.addAttribute("manager_list", manager_list);
//			}
//
//
//		}catch(Exception ex) {
//			resultPage = "cmmn/saveDataFailure";
//			System.out.println(ex.getMessage());
//		}
//		return resultPage;
//	}
//
//	@RequestMapping("/checkSignUp")
//	public String checkSignUp(Locale locale, Model model, HttpServletRequest request) throws Exception {
//		String resultPage = "cmmn/saveSignUpSuccess";
//
//		try {
//			PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//			String user_id = request.getParameter("user_id");
//			String user_pwd = pwdEncoder.encode(request.getParameter("user_pwd"));
//			String user_name = request.getParameter("user_name");
//			String user_sex = request.getParameter("user_sex");
//			String user_age = request.getParameter("user_age");
//			String user_birthday_year = request.getParameter("birthday_year");
//			String user_birthday_month = request.getParameter("birthday_month");
//			String user_birthday_day = request.getParameter("birthday_day");
//			String user_birthday = user_birthday_year+user_birthday_month+user_birthday_day;
//
//			String user_occupation = request.getParameter("user_occupation");
//			String user_manager = request.getParameter("user_manager");
//
//			UserDto user = new UserDto(user_id, user_pwd, user_name, user_sex, user_age, user_birthday, user_occupation, user_manager);
//			dao.insertUserDao(user);
//
//
//		}catch(Exception ex) {
//			resultPage = "cmmn/saveSignUpFailure";
//			System.out.println(ex.getMessage());
//		}
//		return resultPage;
//	}
//
//	 @RequestMapping(value = "/checkDuplicateId", method = RequestMethod.POST)
//	    public String checkDuplicateId(HttpServletRequest request, Model model) {
//		 	PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//	        String id = request.getParameter("id");
//	        int rowcount = dao.checkDuplicateIdDao(id);
//	        return String.valueOf(rowcount);
//	    }
//
//
//	 @RequestMapping("/deleteManager")
//	 public String deleteManager(HttpServletRequest request, Model model) {
//		 String resultPage = "cmmn/successTaskWithSettingForm";
//		 String newManager = "";
//		 Map<String, String> tempMap = new HashMap<String, String>();
//		 try {
//			 PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//			 String manager_id = request.getParameter("manager_id");
//			 manager_id = manager_id.trim();
//			 //�Ʒ��� manager_id �̻��ؼ� ���� �õ� ���� �߻�
//			 if("".equals(manager_id) || dao.checkDuplicateIdDao(manager_id) <= 0) {
//				 return "cmmn/saveDataFailure";
//			 }
//			 UserDto userInfo = dao.selectAllUserWithIdDao(SecurityContextHolder.getContext().getAuthentication().getName().toString());
//				List<UserDto> manager_list = new ArrayList<UserDto>();
//				String[] arrManager = userInfo.getUser_manager().split(",");
//				for(String data : arrManager) {
//					data = data.trim();
//					if(!manager_id.equals(data)) {
//						newManager += data;
//						newManager += ",";
//					}
//				}
//			//������ ������ �޸� ����
//			if(newManager.length() > 0)
//				newManager = newManager.substring(0, newManager.length()-1);
//			tempMap.put("user_manager", newManager);
//			tempMap.put("user_id", SecurityContextHolder.getContext().getAuthentication().getName().toString());
//			dao.updateManagerWithIdDao(tempMap);
//
//		 }catch(Exception ex) {
//			 resultPage = "cmmn/saveDataFailure";
//			 System.out.println(ex.getCause());
//		 }
//
//         return resultPage;
//     }
//
//
//	 @RequestMapping("/checkNewManager")
//	 public String checkNewManager(HttpServletRequest request, Model model) {
//		 String resultPage = "cmmn/successTaskWithSettingForm";
//		 Map<String, String> tempMap = new HashMap<String,String>();
//		 String user_id = "";
//		 String managers = "";
//		 try {
//	 	 PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//	 	 user_id = SecurityContextHolder.getContext().getAuthentication().getName().toString();
//         String manager_id = request.getParameter("manager_id");
//         manager_id = manager_id.trim();
//         //�Ʒ��� manager_id �̻��ؼ� ���� �õ� ���� �߻�
//		 if("".equals(manager_id) || dao.checkDuplicateIdDao(manager_id) <= 0 || user_id.equals(manager_id)) {
//			 return "cmmn/saveManagerFailure";
//		 }
//		 UserDto userInfo = dao.selectAllUserWithIdDao(user_id);
//		 if("".equals(userInfo.getUser_manager()))
//			 managers = (manager_id).trim();
//		 else
//			 managers = (userInfo.getUser_manager() + "," + manager_id).trim();
//
//         tempMap.put("user_manager", managers);
//         tempMap.put("user_id", user_id);
//         dao.updateManagerWithIdDao(tempMap);
//		 }catch(Exception ex) {
//			 resultPage = "cmmn/saveDataFailure";
//		 }
//         return resultPage;
//     }
//
//
//	 @RequestMapping("/checkNewPassword")
//	 public String checkNewPassword(HttpServletRequest request, Model model) {
//		 String resultPage = "cmmn/successTaskWithSettingForm";
//		 Map<String, String> tempMap = new HashMap<String,String>();
//		 String user_id = "";
//		 try {
//	 	 PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//	 	 user_id = SecurityContextHolder.getContext().getAuthentication().getName().toString();
//         String user_pwd = pwdEncoder.encode(request.getParameter("user_pwd"));
//         tempMap.put("user_pwd", user_pwd);
//         tempMap.put("user_id", user_id);
//         dao.updatePwdWithIdDao(tempMap);
//
//		 }catch(Exception ex) {
//			 resultPage = "cmmn/saveDataFailure";
//		 }
//         return resultPage;
//     }
//
//	@RequestMapping("/depInfo")
//	public String depInfo(Locale locale, Model model, HttpServletRequest request) throws Exception {
//		String resultPage = "info/depInfo";
//		return resultPage;
//	}
//
//	@RequestMapping("/panInfo")
//	public String panInfo(Locale locale, Model model, HttpServletRequest request) throws Exception {
//		String resultPage = "info/panInfo";
//		return resultPage;
//	}
//
//	@RequestMapping("/perInfo")
//	public String perLayer(Locale locale, Model model, HttpServletRequest request) throws Exception {
//		String resultPage = "info/perInfo";
//		return resultPage;
//	}
//
//	@RequestMapping("/insInfo")
//	public String insLayer(Locale locale, Model model, HttpServletRequest request) throws Exception {
//		String resultPage = "info/insInfo";
//		return resultPage;
//	}
//
//	@RequestMapping("/intInfo")
//	public String intLayer(Locale locale, Model model, HttpServletRequest request) throws Exception {
//		String resultPage = "info/intInfo";
//		return resultPage;
//	}
//
//	@RequestMapping("/android")
//	public void androidTest(Locale locale, Model model, HttpServletRequest request) throws Exception {
//		System.out.println("Android�� �����߽��ϴ�.");
//		 PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//		 request.setCharacterEncoding("UTF-8");
//		String tempStr = request.getParameter("test");
//		dao.insertTestDao(tempStr);
//
//	}
//
//	/*
//	//@Async("threadPoolTaskExecutor")
//    public void sendEmailToManager(UserDto userInfo, String[] argManagers, String strSort, PredictorMaker pm) throws Exception {
//        SimpleMailMessage email = new SimpleMailMessage();
//		for(String e_mail : argManagers) {
//			System.out.println("���� ������ �ִ� ��!" + e_mail);
//			email.setTo(e_mail);
//			email.setFrom("Precare");
//			email.setSubject("[Precare] ����˸� ���� : "+userInfo.getUser_id()+" ("+userInfo.getUser_name()+")���� "+strSort+" ����� �˷��帳�ϴ�.");
//			email.setText("[Precare] ����˸� ����\n\n***�Ǻ�ȣ�� ��������***\nE-mail : "+userInfo.getUser_id()+"\n���� : "+userInfo.getUser_name()+
//					"\n���� : "+userInfo.getUser_age()+"\n���� : "+userInfo.getUser_occupation()+"\n"+strSort+"�˻��� : "+pm.getStrKrResult()+"\n\n�ڼ��� ������ Precare ����Ʈ���� Ȯ�� �����մϴ�.");
//			try {
//			mailSender.send(email);
//			}catch(MailException me) {
//				System.out.println("���� �߼� ����");
//				me.printStackTrace();
//			}
//		}
//    }
//    */
//
//    public void sendEmailToManager(UserDto userInfo, String[] argManagers, String strSort, PredictorMaker pm) throws Exception {
//        // do something
//		for(String e_mail : argManagers) {
//			email.setReceiver(e_mail);
//			email.setSubject("[Precare] ����˸� ���� : "+userInfo.getUser_id()+" ("+userInfo.getUser_name()+")���� "+strSort+" ����� �˷��帳�ϴ�.");
//			email.setContent("[Precare] ����˸� ����\n\n***�Ǻ�ȣ�� ��������***\nE-mail : "+userInfo.getUser_id()+"\n���� : "+userInfo.getUser_name()+
//					"\n���� : "+userInfo.getUser_age()+"\n���� : "+userInfo.getUser_occupation()+"\n"+strSort+"�˻��� : "+pm.getStrKrResult()+"\n\n�ڼ��� ������ Precare ����Ʈ���� Ȯ�� �����մϴ�.");
//			//emailSender.SendEmail(email);
//
//		}
//    }
//
//
//
//	@RequestMapping("/downloadExcel")
//	public void downloadExcel(Locale locale, Model model, HttpServletRequest request) throws Exception {
//		 PrecareDao dao = sqlSession.getMapper(PrecareDao.class);
//		 request.setCharacterEncoding("UTF-8");
//
//		 String target_id = request.getParameter("target_id");
//		 String que_name = request.getParameter("que_name");
//		 String que_id = "";
//		 if("depression".equals(que_name))
//			 que_id = "001%";
//
//
//
//		 //��� �����ϱ�
//		 File savefile;
//		 String savepathname;
//		 String user_id = SecurityContextHolder.getContext().getAuthentication().getName().toString();
//		 String filename = "survey_result_"+user_id+".JPG";  //�����̸� ���Ƿ� ����
//
//		 JFileChooser chooser = new JFileChooser();// ��ü ����
//		 chooser.setCurrentDirectory(new File("C:\\")); // ��ó����θ� C�� ��
//		 chooser.setFileSelectionMode(chooser.DIRECTORIES_ONLY); // ���丮�� ���ð���
//
//		 int re = chooser.showSaveDialog(null);
//		 if (re == JFileChooser.APPROVE_OPTION) { //���丮�� ����������
//			 savefile = chooser.getSelectedFile(); //���õ� ���丮 �����ϰ�
//		     savepathname = savefile.getAbsolutePath() + "\\" + filename;  //���丮���+�����̸�
//		     System.out.println(savepathname);
//		 }else{
//			 JOptionPane.showMessageDialog(null, "Please, select file.",
//		     "Caution", JOptionPane.WARNING_MESSAGE);
//			 return;
//		 }
//
//		 //���� ���� �ٿ�ε� �ϱ�
//		// ExcelWriter ew = new ExcelWriter(savefile.getAbsolutePath(), filename, dao.selectQuestinoWithSortDao(que_id), ,dao.selectAllUserWithIdDao(user_id));
//		 ExcelWriter ew = new ExcelWriter();
//    	 try {
//     		ew.writeData();
//		 } catch (Exception e) {
//		 	System.out.println("Excel download error!!");
//		 	e.printStackTrace();
//		 }
//
//	}
//
//
//
//}
//
