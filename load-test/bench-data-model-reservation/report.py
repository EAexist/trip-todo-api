import argparse
import json
from datetime import datetime
from pathlib import Path

from dotenv import load_dotenv

from shared.adapters.prometheus_adapter import TimeRange
from shared.reporting.container_resource import get_container_resource_usage
from shared.reporting.latency import (
    get_reservation_repository_find_all_by_id_latency,
    get_reservation_repository_save_all_latency,
)
from shared.reporting.spring_resource import get_spring_resource_usage


def report(test_id):

    summary_file_path = (
        Path(__file__).resolve().parent / f"output/{test_id}/test-summary.json"
    )

    with open(summary_file_path, "r") as f:
        test_summary = json.load(f)
        iterations = test_summary["iterations"]

    # Report Performance Metrics
    time_ranges: list[TimeRange] = []

    for iteration in iterations:
        is_target = iteration.get("is_target", False)
        if not is_target:
            continue
        time_ranges.append(
            TimeRange(
                start_time=datetime.fromisoformat(iteration["start_time"]),
                end_time=datetime.fromisoformat(iteration["end_time"]),
            )
        )

    reports = {
        "reservation_repository_save_all_latency": get_reservation_repository_save_all_latency(
            test_id=test_id, iterations=time_ranges
        ),
        "reservation_repository_find_all_by_id_latency": get_reservation_repository_find_all_by_id_latency(
            test_id=test_id, iterations=time_ranges
        ),
        "spring_resource_usage": get_spring_resource_usage(
            test_id=test_id, iterations=time_ranges
        ),
        "db_resource_usage": get_container_resource_usage(
            container_service_id="db", iterations=time_ranges
        ),
    }

    print(f"reports :\n{'\n'.join([f'\t{k}: {v}' for k, v in reports.items()])}")

    # Save report to results directory
    output_path = Path(summary_file_path).parent / "report.json"
    with open(output_path, "w", encoding="utf-8") as f:
        json.dump(reports, f, indent=2)
    print(f"Report saved to {output_path}")


def main():
    parser = argparse.ArgumentParser(description="Load Test reporting")
    parser.add_argument("--test-id", required=True, help="Test ID")

    args = parser.parse_args()

    load_dotenv()
    report(
        args.test_id,
    )


if __name__ == "__main__":
    main()
