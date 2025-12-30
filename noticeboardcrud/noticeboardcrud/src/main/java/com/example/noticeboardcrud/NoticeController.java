package com.example.noticeboardcrud;



import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeRepository repo;

    public NoticeController(NoticeRepository repo) {
        this.repo = repo;
    }

    // GET all notices
    @GetMapping
    public List<Notice> getAll() {
        return repo.findAll();
    }

    // POST add notice
    @PostMapping
    public String addNotice(@RequestBody Notice notice) {
        repo.save(notice);
        return "Notice added successfully";
    }

    // DELETE notice by id
    @DeleteMapping("/{id}")
    public String deleteNotice(@PathVariable int id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return "Notice deleted!";
        }
        return "Notice not found!";
    }

    // PUT update notice
    @PutMapping("/{id}")
    public String updateNotice(@PathVariable int id,
                               @RequestBody Notice updatedNotice) {

        return repo.findById(id)
                .map(notice -> {
                    notice.setTitle(updatedNotice.getTitle());
                    notice.setDescription(updatedNotice.getDescription());
                    repo.save(notice);
                    return "Notice updated successfully";
                })
                .orElse("Notice not updated");
    }
}

