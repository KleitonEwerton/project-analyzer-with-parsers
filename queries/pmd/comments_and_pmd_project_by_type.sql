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
        project_name = 'project_name'
    GROUP BY
        c.hash
),
pmd_count AS (
    SELECT
        p.hash AS commit_hash,
        p.type AS issue_type,
        COUNT(p.id) AS total_pmd_issues
    FROM
        public.pmd p
    GROUP BY
        p.hash, p.type
)
SELECT
    cs.commit_hash,
    pc.issue_type,
    COALESCE(pc.total_pmd_issues, 0) AS total_pmd_issues,
    -- Colunas de diferenças
    (cs.total_comment_block - cs.total_parent_comment_block) AS difference_block,
    (cs.total_comment_doc - cs.total_parent_comment_doc) AS difference_doc,
    (cs.total_comment_line - cs.total_parent_comment_line) AS difference_line,
    (cs.total_segments - cs.total_parent_segments) AS difference_segments
FROM
    comment_summary cs
LEFT JOIN
    pmd_count pc
ON
    cs.commit_hash = pc.commit_hash
WHERE
    COALESCE(pc.total_pmd_issues, 0) > 0
ORDER BY
    cs.commit_hash;
