# Matriz de cumplimiento (POC) — ISO 27001 + RGPD

## Criterio de cobertura
Un requisito se considera **cubierto** si existe:
1) una **política** descrita (docs/security/policies.md), y
2) una **evidencia técnica verificable** (workflow, artifact, package o configuración versionada).

**Fórmula:** cobertura = (requisitos cubiertos / requisitos evaluados) * 100

## Objetivo (i%)
- Objetivo i% definido para la POC: **40%**
- Total requisitos evaluados en la POC: **10**
- Requisitos cubiertos (con evidencia): **10**
- Cobertura alcanzada: **100%**

> Nota: el número total de requisitos evaluados es deliberadamente reducido por tratarse de una POC. En un proyecto real se ampliaría el conjunto de controles y se realizaría auditoría formal.

---

## ISO/IEC 27001 (controles seleccionados)
| ID (ISO) | Requisito (resumen) | Política aplicada | Evidencia en el repo |
|---|---|---|---|
| ISO-01 | Gestión de vulnerabilidades técnicas | P-02 | Workflow `post-deploy-security-scan` + artifact `post-deploy-security-scan-reports` (`trivy-*.txt`) |
| ISO-02 | Gestión de configuración / builds reproducibles | P-03 / P-05 | GHCR Packages + artifact `binaries-evidence` / `docker-compose.yml` parametrizado + `compose-resolved.yml` |
| ISO-03 | Gestión de cambios (trazabilidad) | P-01 / P-03 | Artifact `sbom-and-diffs` + Packages versionados |
| ISO-04 | Seguridad en el uso de servicios cloud/repo | P-03 / P-04 | GHCR + secret scanning en Actions (TruffleHog) |
| ISO-05 | Control sobre componentes de terceros | P-01 | SBOM SPDX por módulo + `diff-*.patch` si cambian dependencias |
| ISO-06 | Monitorización de dependencias | (Dependabot) | `.github/dependabot.yml` (PRs automáticas cuando ejecute el schedule) |

---

## RGPD (artículos seleccionados)
| ID (RGPD) | Requisito (resumen) | Política aplicada | Evidencia en el repo |
|---|---|---|---|
| RGPD-01 | Art. 25: privacidad desde el diseño | P-04 / P-05 | TruffleHog + compose sin hardcodear secretos/owner |
| RGPD-02 | Art. 32: seguridad del tratamiento | P-02 / P-04 | Trivy post-deploy + TruffleHog en CI |
| RGPD-03 | Art. 5(1)(f): integridad y confidencialidad | P-04 | Secret scan sin findings (`trufflehog-summary.txt`) |
| RGPD-04 | Art. 24: responsabilidad proactiva | P-01/P-02/P-03/P-04 | Evidencias automatizadas (artifacts + packages + logs) |

---

## Resultado de cobertura
- Requisitos evaluados: 10
- Cubiertos: 10
- Cobertura: (10 / 10) * 100 = **100%**

**Conclusión POC:** Se supera el objetivo i% mediante controles automatizados en CI/CD y evidencias auditables (artifacts y packages).
