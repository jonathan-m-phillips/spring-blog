package com.codeup.blog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
class RollDiceServlet {

   @GetMapping("/roll-dice")
    public String showDiceForm() {
        return "RollDice";
    }

    @GetMapping("/roll-dice")
    public String showDiceResult(@RequestParam(name = "number") int num, Model model) {
       int randomNum = (int)((Math.random() * 6) + 1);
       String message = "you selected " + num + " and the number rolled was " + randomNum + ".";

       if(num == randomNum) {
           message += " You won!";
       } else {
           message += " You lose.";
       }
       model.addAttribute("message", message);
       return "RollDice";
    }

}
