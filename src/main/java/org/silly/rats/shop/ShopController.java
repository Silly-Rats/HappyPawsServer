package org.silly.rats.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopController {
	@RequestMapping(path = "/shop")
	public String mainPage() {
		return "shop/index.html";
	}
}