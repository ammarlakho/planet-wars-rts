
import subprocess
from pathlib import Path
from typing import List

from runner_utils.agent_entry import AgentEntry

import os
from pathlib import Path

user = os.getenv("USER", "default")
run_id = "planetwars-run"
base_dir = Path(f"/tmp/{user}-{run_id}")
base_dir.mkdir(parents=True, exist_ok=True)


def run_command(cmd: list[str], cwd: Path=None):
    print(f"Running: {' '.join(cmd)} (in {cwd or Path.cwd()})")
    subprocess.run(cmd, check=True, cwd=cwd)


def launch_agent(agent: AgentEntry, base_dir: Path):
    repo_dir = base_dir / agent.id

    # Clone if needed
    if not repo_dir.exists():
        run_command(["git", "clone", agent.repo_url, agent.id])
    else:
        # Repo exists: make sure we fetch latest commits in case we want one
        run_command(["git", "fetch"], cwd=repo_dir)

    # Checkout specified commit (always do this if provided)
    if agent.commit:
        run_command(["git", "checkout", agent.commit], cwd=repo_dir)

    # Build image
    run_command(["podman", "build", "-t", f"game-server-{agent.id}", "."], cwd=repo_dir)

    # Run container
    run_command([
        "podman", "run", "-d",
        "-p", f"{agent.port}:8080",
        "--name", f"container-{agent.id}",
        f"game-server-{agent.id}"
    ])
