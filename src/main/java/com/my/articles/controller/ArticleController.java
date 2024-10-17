package com.my.articles.controller;

import com.my.articles.dto.ArticleDTO;
import com.my.articles.dto.CommentDTO;
import com.my.articles.entity.Comment;
import com.my.articles.repository.ArticleRepository;
import com.my.articles.service.ArticleService;
import com.my.articles.service.PaginationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("articles")
@Slf4j
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    PaginationService paginationService;

    @GetMapping("")
    public String showAllArticles(Model model, @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//        List<ArticleDTO> articles = articleService.showAllArticles();
//        log.info("### article controller - articles :" + articles);
        Page<ArticleDTO> paging = articleService.getArticlePage(pageable);

        //페이징 정보 확인
        //전체 페이지 수
        int totalPage = paging.getTotalPages();
        int currentPage = paging.getNumber();
        System.out.println("totalPage="+totalPage);
        System.out.println("currentPage="+currentPage);

        //페이지 블럭 처리
        List<Integer> barNumbers = paginationService.getPaginationBarNumber(currentPage, totalPage);
        System.out.println("#####"+barNumbers.toString());

        //모델로 보내기
        model.addAttribute("pageBars", barNumbers);
        model.addAttribute("articles", paging);
        return "/articles/show-all";
    }

    @GetMapping("new")
    public String newArticle(Model model) {
        model.addAttribute("dto", new ArticleDTO());
        return "/articles/new";
    }

    @PostMapping("create")
    private String createArticle(ArticleDTO dto, RedirectAttributes redirectAttributes) {
        log.info("### get new article : " + dto);
        articleService.addArticle(dto);
        redirectAttributes.addFlashAttribute("msg", "새로운 게시글이 등록되었습니다.");
        return "redirect:/articles";
    }

    @GetMapping("{id}")
    public String showOneArticle(@PathVariable("id") Long id, Model model) {
        log.info("### article id: "+id);
        ArticleDTO article = articleService.findOneArticle(id);
        log.info("### article controller - article :" + article);
        model.addAttribute("dto", article);
        model.addAttribute("newComment", new CommentDTO());
        return "/articles/show";
    }

    @GetMapping("{id}/update")
    public String viewUpdateArticle(@PathVariable("id") Long id, Model model) {
        //원래 게시글 내용 출력
        model.addAttribute("dto", articleService.findOneArticle(id));
        return "/articles/update";
    }

    @PostMapping("update")
    public String updateArticle(ArticleDTO dto, RedirectAttributes redirectAttributes) {
        log.info("### updated article : " + dto);
        articleService.updateArticle(dto);
        String url = "redirect:" + dto.getId();
        redirectAttributes.addFlashAttribute("msg", "게시글이 수정되었습니다.");
        return url;
    }

    @GetMapping("{id}/delete")
    public String deleteArticle(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        log.info("### delete article id: "+id);
        articleService.deleteArticle(id);
        redirectAttributes.addFlashAttribute("msg", "게시글이 삭제되었습니다.");
        return "redirect:/articles";
    }

}
