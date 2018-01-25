package zm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sun.jvm.hotspot.oops.Mark;
import zm.service.course.*;

import java.util.List;

@Controller
@RequestMapping(value="/zm", method = RequestMethod.GET)
public class ZMController {

    @Autowired
    private Courses courses;

    @Autowired
    private Markdown2Html m2hConverter;


    //
    // 登录
    //
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(ModelMap model) {

        return "zm/login";
    }

    /***
     * 请求显示所有课程
     * @param model
     * @return 视图名称
     */
    @RequestMapping(value="/courses", method = RequestMethod.GET)
    public String getCourses(ModelMap model){

        model.addAttribute("courses", courses.getCourseMetas());

        return "zm/courses";
    }

    @RequestMapping(value="/courses/new", method=RequestMethod.GET)
    public String newCourse(ModelMap model){

        return "zm/course_new";
    }

    /***
     * 具体课程页面
     * @param model
     * @return 视图名称
     */
    @RequestMapping(value="/course", method = RequestMethod.GET)
    public String getCourse(ModelMap model,
                            @RequestParam("name") String courseName){

        model.addAttribute( "back_url", "/zm/courses");
        model.addAttribute( "courseName", courseName);

        List<UnitMeta> meta = courses.getUnitMetas(courseName);
        model.addAttribute("unitMetas", meta);

        return "zm/course";
    }

    @RequestMapping(value = "/add_course", method = RequestMethod.GET)
    public String addCourse(@RequestParam("name") String courseName){

        return "";
    }

    /**
     * 显示单元内容页面
     */
    @RequestMapping(value="/unit", method = RequestMethod.GET)
    public String getUnit(ModelMap model,
                          @RequestParam("course") String courseName,
                          @RequestParam("unit") String unitName){

        model.addAttribute( "back_url", "/zm/course?name=" + courseName);
        model.addAttribute( "unitIcon", courses.getUnitIconUrl(courseName, unitName));

        List<LessonMeta> lessonMetas = courses.getLessonMetas( courseName, unitName);
        model.addAttribute( "lessonMetas", lessonMetas);

        return "zm/unit";
    }

    /**
     * 显示课程内容
     */
    @RequestMapping(value="/lesson", method = RequestMethod.GET)
    public String getLesson(ModelMap model,
                            @RequestParam("course") String courseName,
                            @RequestParam("unit") String unitName,
                            @RequestParam("lesson") String lessonName){

        model.addAttribute( "back_url", "/zm/course?name=" + courseName + "&unit=" + unitName);
        Lesson lesson = courses.getLesson( courseName, unitName, lessonName);
        model.addAttribute("lesson", lesson);

        return "zm/lesson";
    }
}
