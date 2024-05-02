package org.example.wechat.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.example.wechat.commonIO.Message;
import org.example.wechat.entity.Exercitation;
import org.example.wechat.service.ExercitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XStreamAlias("xml")
@RestController
public class OfferController {

    @Autowired
    private ExercitationService exercitationService;
    @PostMapping("/addOffer")
    public String addOffer(Map<String, String> map){
        int state = 1;
        Message message1 = new Message();
        String[] strings = map.get("Content").split(" ");

        if (strings[1] == null || strings[3] == null || strings[4] == null){
            throw new RuntimeException("输入不能有空");
        }
        Exercitation exercitation = new Exercitation(strings[1], strings[2], Integer.valueOf(strings[3]), Integer.valueOf(strings[4]));
        exercitation.toString();
        try {
            exercitationService.addExercitation(exercitation);
            return message1.ActionMsgReturn(state, map);
        }catch (Exception e){
            return message1.failMsgReturn(map);
        }
    }

    @PostMapping("/deleteOffer")
    public String deleteOffer(Map<String, String>map){
        int state = 2;
        Message message1 = new Message();
        String[] strings = map.get("Content").split(" ");

        if (strings[1] == null || strings[3] == null || strings[4] == null){
            throw new RuntimeException("输入不能有空");
        }
        Exercitation exercitation = new Exercitation(strings[1], strings[2], Integer.valueOf(strings[3]), Integer.valueOf(strings[4]));
                //exercitation.toString();
        Map<String, Object> deleMap = new HashMap<>();
        deleMap.put("company", strings[1]);
        deleMap.put("work_name", strings[2]);
        deleMap.put("salary", strings[3]);
        deleMap.put("work_quantity", strings[4]);
        try {
            exercitationService.deleExercitation(deleMap);
            return message1.ActionMsgReturn(state, map);
        }catch (Exception e){
            return message1.failMsgReturn(map);
        }
    }

    @PostMapping("/updateOffer")
    public String updateOffer(Map<String, String> map){
        int state = 3;
        Message message1 = new Message();
        String[] strings = map.get("Content").split(" ");

        if (strings[1] == null || strings[3] == null || strings[4] == null){
            throw new RuntimeException("输入不能有空");
        }
        Exercitation exercitation = new Exercitation(strings[1], strings[2], Integer.valueOf(strings[3]), Integer.valueOf(strings[4]));
        try {
            exercitationService.updateExercitation(exercitation);
            return message1.ActionMsgReturn(state, map);
        }catch (Exception e){
            return message1.failMsgReturn(map);
        }
    }

    @PostMapping("/findOffer")
    public String findOffer(Map<String, String> map){
        int state = 4;
        Message message1 = new Message();
        String[] strings = map.get("Content").split(" ");

        if (strings[1] == null || strings[3] == null ){
            throw new RuntimeException("输入不能有空");
        }
        Exercitation exercitation = new Exercitation(strings[1]);
        try {
            Page<Exercitation> page = new Page<>(Integer.valueOf(strings[2]), Integer.valueOf(strings[3]));
            System.out.println(Integer.valueOf(strings[2]) + " + " + Integer.valueOf(strings[3]));
            IPage<Exercitation> exercitationIPage = exercitationService.selectPage(page, exercitation);

            StringBuilder stringBuilder = new StringBuilder();
            List<Exercitation> list = exercitationIPage.getRecords();

            int count = 0;
            for (Exercitation exercitation1 : list){
                if (count == Integer.valueOf(strings[2])*Integer.valueOf(strings[3])+ Integer.valueOf(strings[3])-1){
                    System.out.println(count);
                    break;
                }
                if (count >= Integer.valueOf(strings[2])*Integer.valueOf(strings[3])-1){
                    stringBuilder.append(exercitation1.toString()).append("\n");
                }

                count++;
            }
            int remainder = list.size() % Integer.valueOf(strings[3]);
            int totalPage ;
            if (remainder != 0){
                totalPage = list.size() / Integer.valueOf(strings[3]) + 1;
            }else {
                totalPage = list.size() / Integer.valueOf(strings[3]);
            }

            System.out.println(stringBuilder.toString());
            map.put("Content", stringBuilder.toString() + "\n" + Integer.valueOf(strings[2]) + '/' + totalPage);
            return message1.findPageMsg(map);
        } catch (Exception e) {
            return message1.failMsgReturn(map);
        }
    }
}
