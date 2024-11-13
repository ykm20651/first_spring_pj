package com.example.deliveryproject.Controller;

import com.example.deliveryproject.Dto.OrderDTO;
import com.example.deliveryproject.Dto.OrderRequest;
import com.example.deliveryproject.Dto.OrderUpdateRequest;
import com.example.deliveryproject.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 메뉴 조회 - 정적 페이지 반환
    @GetMapping("/menus")
    public String getMenuPage() {
        return "menu"; //이 페이지는 src/main/resources/static/menu.html에 위치해야 함.
    }

    // 모든 주문 조회
    @GetMapping("/orders")
    public String getAllOrders(Model model) { //클라이언트 요청에 따른 작업 처리 결과 데이터 Model에 저장하기 위해 파라미터에 넣음.
            List<OrderDTO> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        //조회된 주문 리스트를 "orders"라는 이름으로 모델에 추가함.
        return "orders"; //"orders"라는 view를 반환함. -> static이나 templates 파일 내 orders.html을 반환.
    }
    //Model 객체는 컨트롤러가 처리한 데이터를 뷰(View) 템플릿으로 전달하는 역할을 함.
    // 예를 들어, 데이터를 HTML 페이지에 표시해야 할 때 Model에 데이터를 넣어두면, 템플릿에서 그 데이터를 사용할 수 있음.

    // 주문 추가
    @PostMapping("/orders")
    public String addOrder(@RequestParam String menuName, @RequestParam Double menuPrice, @RequestParam Integer quantity, Model model) {
        //DTO는 클라이언트가 요청한 것에 대해 필요한 데이터만 반환하는 것이 목적이기에 새로주문하거나 기존 주문 수정등 DTO는 나눠서 설계하는 것임.
        OrderRequest request = new OrderRequest(menuName, menuPrice, quantity); //OrderRequest DTO 객체 생성
        OrderDTO order = orderService.addOrder(request); //OrderDTO 객체 생성 하여 새 주문 추가한거 저장.
        model.addAttribute("order", order); //결국 클라이언트 요청에 따른 작업 처리 결과 데이터를 담는 Model 객체에 DTO로 반환하기 위해 DTO타입의 객체를 넘겨주는 것
        return "orderDetail";
    }

    // 주문 수정 - 주문 수정에 관한 데이터 처리는 OrderUpdateRequest DTO에서 처리함.
    @PutMapping("/orders/{id}")
    public String updateOrder(@PathVariable Long id, @RequestParam Integer quantity, @RequestParam String status, Model model) {
        //@PathVariable은 URL 경로에 포함된 값을 받을 때 사용함!
        OrderUpdateRequest updateRequest = new OrderUpdateRequest(quantity, status);
        OrderDTO updatedOrder = orderService.updateOrder(id, updateRequest);
        //Q)주문 수정의 경우 OrderUpdateRequest DTO클래스에서 처리하려고 했는데, 그걸 왜 또 OrderDTO에 넘어가서 처리??
        //A) OrderUpdateRequest Dto 클래스는 주문 수정 요청에 대하여 필요한 데이터만을 담고 있음.
        // 반면, OrderDTO 클래스는 주문 데이터 전송 시 사용되는 DTO로, 주문에 대한 전체 정보를 담고 있음.

        //전체 과정
        //1. OrderUpdateRequest는 사용자로부터 quantity와 status만 받아서 수정할 데이터를 담아 보내는 역할.
        //2. OrderService는 id와 updateRequest를 받아서, 해당 주문을 수정하는 작업을 수행함.
        //즉, OrderUpdateRequest를 기반으로 실제 주문 데이터를 수정하고, 수정된 데이터를 다시 OrderDTO로 변환해서 반환
        //3. orderService.updateOrder 메서드에서 처리된 수정된 주문 정보를 OrderDTO 형태로 반환하는 이유는,
        // 수정된 주문의 모든 데이터를 뷰(View)에 전달할 때 OrderDTO가 전체 주문 데이터를 포함하고 있기 때문임!!
        model.addAttribute("order", updatedOrder); //수정된 주문 데이터를 order라는 이름으로 모델에 추가.
        return "orderDetail";
    }

    // 주문 삭제
    @DeleteMapping("/orders/{id}")
    public String deleteOrder(@PathVariable Long id, Model model) {
        orderService.deleteOrder(id);
        model.addAttribute("message", "Order canceled successfully.");
        return "orders";
    }
}
