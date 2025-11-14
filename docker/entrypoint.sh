#!/usr/bin/env bash
set -euo pipefail

REPORT_DIR="${REPORT_DIR:-/reports}"
SELENIUM_REMOTE_URL="${SELENIUM_REMOTE_URL:-http://selenium-hub:4444/wd/hub}"

mkdir -p "${REPORT_DIR}"

echo "---- Starting test run ----"
echo "SELENIUM_REMOTE_URL=${SELENIUM_REMOTE_URL}"
echo "REPORT_DIR=${REPORT_DIR}"

export SELENIUM_REMOTE_URL

# If custom args are provided (from Kubernetes Job), run those instead
if [ $# -gt 0 ]; then
  echo "Running custom command: $@"
  exec "$@"
fi

# Default behavior (if no args passed)
mvn -B -DskipTests=false test
MVN_STATUS=$?

# Copy common report folders
REPORT_SOURCES=("Run Result Folder" "reports" "test-output" "target/surefire-reports" "allure-results")
for src in "${REPORT_SOURCES[@]}"; do
  if [ -d "${src}" ]; then
    echo "Copying ${src} to ${REPORT_DIR}"
    cp -r "${src}" "${REPORT_DIR}/" || true
  fi
done

echo "Reports available under ${REPORT_DIR}:"
ls -la "${REPORT_DIR}" || true

exit ${MVN_STATUS}
