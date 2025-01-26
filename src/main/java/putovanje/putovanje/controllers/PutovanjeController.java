package putovanje.putovanje.controllers;

import putovanje.putovanje.models.Putovanje;
import putovanje.putovanje.models.Putovanje;
import putovanje.putovanje.models.User;
import putovanje.putovanje.models.UserDetails;
import putovanje.putovanje.repositories.PutovanjeRepository;
import putovanje.putovanje.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import putovanje.putovanje.services.PutovanjeService;

import java.time.format.DateTimeFormatter;



import java.util.List;

    @Controller
    public class PutovanjeController {

        @Autowired
        private PutovanjeRepository reviewRepository;
        @Autowired
        private PutovanjeService putovanjeService;

        @Autowired
        private UserRepository userRepository;


        @GetMapping("/home")
        public String getAllPutovanja(
                @RequestParam(value = "page", defaultValue = "0") Integer page,
                @RequestParam(value = "size", defaultValue = "5") Integer size,
                Model model) {

            if (page < 0) {
                page = 0;
            }
            if (size <= 0) {
                size = 5;
            }

            Page<Putovanje> putovanjePage = putovanjeService.findPaginated(page, size);

            // Formatiraj datum za svaki Putovanje objekt
            for (Putovanje putovanje : putovanjePage.getContent()) {
                putovanje.setFormattedDatumDodavanja(putovanje.getDatumDodavanja().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }

            model.addAttribute("osvrti", putovanjePage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", putovanjePage.getTotalPages());
            model.addAttribute("size", size);

            return "home";
        }

        @GetMapping("/profile")
        public String getCurrentUserReviews(@AuthenticationPrincipal UserDetails userDetails, Model model) {
            model.addAttribute("putovanje", new Putovanje());
            List<Putovanje> userReviews = reviewRepository.findByUserId(userDetails.getId());


            // Formatiraj datum za svaku recenziju korisnika
            for (Putovanje putovanje : userReviews) {
                putovanje.setFormattedDatumDodavanja(putovanje.getDatumDodavanja().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            }



            model.addAttribute("user_reviews", userReviews);
            return "profile";
        }




        @PostMapping("/osvrti/add")
        public String addReview(@Valid Putovanje review, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails userDetails) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("osvrti", reviewRepository.findAll());
                return "profile";
            }
            User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            review.setUser(user);
            reviewRepository.save(review);
            return "redirect:/profile";
        }
        @PostMapping("/osvrti/delete/{id}")
        public String deleteReview(@PathVariable Long id) {
            reviewRepository.deleteById(id);
            return "redirect:/profile";
        }

        @PostMapping("/osvrti/edit")
        public String editReview(@ModelAttribute Putovanje putovanje, @AuthenticationPrincipal UserDetails userDetails) {
            User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Putovanje existingReview = reviewRepository.findByIdAndUser(putovanje.getId(), user)
                    .orElseThrow(() -> new IllegalArgumentException("Review not found or not authorized"));

            existingReview.setNazivDestinacije(putovanje.getNazivDestinacije());
            existingReview.setNazivDrzave(putovanje.getNazivDrzave());
            existingReview.setOcjena(putovanje.getOcjena());
            existingReview.setOsvrt(putovanje.getOsvrt());

            reviewRepository.save(existingReview);
            return "redirect:/profile";
        }
    }


