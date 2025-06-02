
if __name__ == "__main__":
    from runner_utils.agent_entry import sample_entries
    from runner_utils.launch_agent import launch_agent, base_dir

    for agent in sample_entries:
        print(f"Launching agent: {agent.id}")
        launch_agent(agent, base_dir)
        print(f"Agent {agent.id} launched successfully.")

