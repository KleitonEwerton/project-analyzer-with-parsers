WITH comment_summary AS (
    SELECT
        c.hash AS commit_hash,
        SUM(c.qnt_comment_block) AS total_comment_block,
        SUM(c.qnt_comment_doc) AS total_comment_doc,
        SUM(c.qnt_comment_line) AS total_comment_line,
        SUM(c.parent_qnt_comment_block) AS total_parent_comment_block,
        SUM(c.parent_qnt_comment_doc) AS total_parent_comment_doc,
        SUM(c.parent_qnt_comment_line) AS total_parent_comment_line,
        SUM(c.qnt_segmentos) AS total_segments,
        SUM(c.parent_qnt_segmentos) AS total_parent_segments
    FROM
        public.comment c
    WHERE 
        project_name like 'project_name'
    GROUP BY
        c.hash
),
refactoring_count AS (
    SELECT
        r.hash AS commit_hash,
        COUNT(r.id) AS total_refactorings
    FROM
        public.refactoring r
    GROUP BY
        r.hash
)
SELECT
    cs.commit_hash,
    COALESCE(rc.total_refactorings, 0) AS total_refactorings,
    -- Colunas de diferenças
    (cs.total_comment_block - cs.total_parent_comment_block) AS difference_block,
    (cs.total_comment_doc - cs.total_parent_comment_doc) AS difference_doc,
    (cs.total_comment_line - cs.total_parent_comment_line) AS difference_line,
    (cs.total_segments - cs.total_parent_segments) AS difference_segments
FROM
    comment_summary cs
LEFT JOIN
    refactoring_count rc
ON
    cs.commit_hash = rc.commit_hash
ORDER BY
    cs.commit_hash;