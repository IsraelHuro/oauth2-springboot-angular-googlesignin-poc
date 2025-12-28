# Políticas de seguridad y cumplimiento (POC)

Este repositorio implementa un conjunto de políticas orientadas a **reducir el riesgo de brecha de seguridad** durante el ciclo de vida del software.  
Al tratarse de una **POC**, las políticas se han elegido para maximizar evidencia técnica automática dentro de GitHub (**Actions, artifacts y Packages**), de forma que sea **reproducible y auditable**.

---

## P-01. Gestión y trazabilidad de dependencias (SBOM)

**Objetivo:** Disponer de un inventario de componentes (Software Bill of Materials) por cada módulo y detectar cambios de dependencias de forma temprana.

**Cómo se aplica (en esta POC):**
- Se genera un SBOM por módulo (`authorization-server`, `resource-server`, `ag-prueba-front`) en formato **SPDX JSON**.
- Se compara el SBOM generado frente a un baseline (si existe) y, si hay diferencias, se genera un fichero `diff-*.patch` como evidencia.

**Automatización:**
- Workflow: `.github/workflows/sbom.yml` (nombre: `sbom-control`).

**Evidencia esperada:**
- Artifact `sbom-and-diffs` con:
  - `*.spdx.json` (SBOM de cada módulo)
  - `diff-*.patch` (solo cuando hay cambios reales de dependencias)
- Logs del workflow y/o resumen del job indicando si el SBOM ha cambiado.

---

## P-02. Escaneo de vulnerabilidades tras el despliegue

**Objetivo:** Reducir el riesgo de introducir vulnerabilidades conocidas en imágenes/entornos desplegados, validando el estado **después** del despliegue (post-deploy).

**Cómo se aplica (en esta POC):**
- Se construye el stack con Docker Compose en el runner.
- Se levantan contenedores (`docker compose up -d`) y se realiza un escaneo con **Trivy** sobre las imágenes construidas.
- El escaneo genera reportes en texto para revisión (sin bloquear el pipeline en modo POC).

**Automatización:**
- Workflow: `.github/workflows/post-deploy-scan.yml` (nombre: `post-deploy-security-scan`).

**Evidencia esperada:**
- Artifact `post-deploy-security-scan-reports` con:
  - `trivy-authorization-server.txt`
  - `trivy-resource-server.txt`
  - `trivy-frontend.txt`
  - `compose-resolved.yml` (config final resuelta)
  - `docker-images.txt`, `docker-ps.txt`, etc. (evidencia de despliegue en runner)

---

## P-03. Almacenamiento y versionado de binarios (imágenes)

**Objetivo:** Asegurar trazabilidad, repetibilidad del despliegue y disponibilidad de “binarios” versionados (en esta POC, **imágenes Docker**).

**Cómo se aplica (en esta POC):**
- Se construyen imágenes para `authorization-server`, `resource-server` y `ag-prueba-front`.
- En ejecuciones manuales (`workflow_dispatch`) o por tag, se publican en **GHCR** (`ghcr.io`) con versión/tag.

**Automatización:**
- Workflow: `.github/workflows/publish-binaries.yml` (nombre: `publish-binaries`).

**Evidencia esperada:**
- Sección **Packages** del repositorio mostrando las imágenes publicadas y sus tags/versiones.
- Artifact `binaries-evidence` con:
  - `published-images.txt` (qué se publicó y con qué versión)
  - `docker-images.txt` (listado de imágenes en el runner)

---

## P-04. Prevención de exposición de secretos en el repositorio

**Objetivo:** Evitar que credenciales/tokens queden almacenados en Git sin mecanismos de detección y contención.

**Cómo se aplica (en esta POC):**
- Secret scan automatizado con **TruffleHog** sobre el repo (comparando cambios respecto a `develop` o la base de la PR).
- Si se detectan secretos, el workflow **falla** (para que no pase “silenciosamente”).

**Automatización:**
- Workflow: `.github/workflows/secret-scan.yml` (nombre: `security-secrets`).

**Evidencia esperada:**
- Ejecución del workflow indicando `Result: ✅ no findings` cuando no hay hallazgos.
- Artifact `secret-scan-trufflehog` con:
  - `trufflehog-report.json`
  - `trufflehog-summary.txt`

---

## P-05. Configuración segura por defecto en despliegue local/CI

**Objetivo:** Reducir “config drift”, evitar hardcodeo de valores sensibles y facilitar despliegue reproducible.

**Cómo se aplica (en esta POC):**
- `docker-compose.yml` parametrizado por variables (`REGISTRY`, `TAG`, `GHCR_OWNER`, etc.).
- Permite ejecutar local/CI sin acoplar el stack a un owner fijo o a valores “quemados”.

**Evidencia esperada:**
- Archivo `docker-compose.yml` parametrizado en el repositorio.
- Workflow post-deploy generando `compose-resolved.yml` como evidencia de la configuración final aplicada.

---
