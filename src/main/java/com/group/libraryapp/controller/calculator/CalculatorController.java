package com.group.libraryapp.controller.calculator;

import com.group.libraryapp.dto.calculator.request.CalculatorAddReqeust;
import com.group.libraryapp.dto.calculator.request.CalculatorMultiplyRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class CalculatorController {

    @GetMapping("/add") // GET /add
    public int addTwoNumbers(@RequestParam int number1, @RequestParam int number2
    ){
        return number1 + number2;
    }

    @GetMapping("/add2") // GET /add
    public int addTwoNumbers(CalculatorAddReqeust request
    ){
        return request.getNumber1() + request.getNumber2();
    }

    @PostMapping("/multiply") // POST / multiply
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request){//post
        return request.getNumber1() * request.getNumber2();
    }

}

