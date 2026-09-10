.PHONY: bench-architecture-msg-broker bench-data-model-reservation-baseline llm-latency-approx

# Default values for arguments
TEST_ID ?=
TAG ?=
N_ITERATIONS ?= 2
SCRIPT ?= 000-smoke-test.js

# Main
bench-architecture-msg-broker:
	@python -m bench-architecture-msg-broker.run --test-id $(TEST_ID) --n-iterations $(N_ITERATIONS) --script $(SCRIPT) --target-tag $(TAG)
	
bench-architecture-msg-broker-baseline:
	@$(MAKE) bench-architecture-msg-broker TEST_ID=$(TEST_ID) N_ITERATIONS=5 SCRIPT=002-breakpoint-test.js TAG=bench-architecture-msg-broker-baseline

bench-architecture-msg-broker-smoke-test:
	@$(MAKE) bench-architecture-msg-broker TEST_ID=$(TEST_ID) N_ITERATIONS=2 SCRIPT=000-smoke-test.js TAG=bench-architecture-msg-broker-baseline

bench-architecture-msg-broker-probe-break-test:
	@$(MAKE) bench-architecture-msg-broker TEST_ID=$(TEST_ID) N_ITERATIONS=1 SCRIPT=001-probe-breakpoint-test.js

run-prometheus:
	@docker start prometheus

llm-latency-approx:
	@cd llm-mock-server/llm-latency-approximation && \
    source .venv/Scripts/activate && \
    python measure_latency.py

bench-data-model-reservation-baseline:
	@python -m bench-data-model-reservation.run --test-id $(TEST_ID) --target-tag bench-data-model-reservation-baseline

bench-data-model-reservation-target:
	@python -m bench-data-model-reservation.run --test-id $(TEST_ID) --target-tag bench-data-model-reservation-target

# Dev
docker-build-alloy-with-curl:
	@cd alloy && \
	docker build . -t grafana-alloy-with-curl:latest

bench-architecture-msg-broker-report:
	@$(MAKE) bench-architecture-msg-broker.report --test-id $(TEST_ID) --n-iterations $(N_ITERATIONS)

bench-data-model-reservation-report:
	@python -m bench-data-model-reservation.report --test-id $(TEST_ID)