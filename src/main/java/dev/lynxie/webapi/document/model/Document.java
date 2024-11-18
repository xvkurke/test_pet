package dev.lynxie.webapi.document.model;

import dev.lynxie.webapi.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(
        name = "documents",
        indexes = {
                @Index(name = "by_user", columnList = "userId"),
                @Index(name = "by_user_parent", columnList = "userId, parentDocument")
        }
)
@RequiredArgsConstructor
public class Document {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "is_archived", nullable = false)
    private Boolean isArchived;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "icon")
    private String icon;

    @Column(name = "is_published", nullable = false)
    private Boolean isPublished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_document_id")
    private Document parentDocument;

    @OneToMany(mappedBy = "parentDocument", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Document> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
