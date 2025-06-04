import subprocess
import sys
import time

def run_in_new_terminal(command):
    # Start a new terminal and run the command
    subprocess.Popen(['cmd', '/c', 'start', 'cmd', '/k'] + command)

if __name__ == "__main__":
    run_in_new_terminal([".\\bin\\windows\\zookeeper-server-start.bat", ".\\config\\zookeeper.properties"])
    time.sleep(10)
    run_in_new_terminal([".\\bin\\windows\\kafka-server-start.bat", ".\\config\\server.properties"])

    if len(sys.argv) > 1:
        if not isinstance(sys.argv[1], str):
            sys.exit(1)
        topic = sys.argv[1]
        partition = sys.argv[2] if len(sys.argv) >= 3 else "1"
        replication = sys.argv[3] if len(sys.argv) >= 4 else "1"
        time.sleep(10)
        run_in_new_terminal([
            ".\\bin\\windows\\kafka-topics.bat", 
            "--create", "--topic", topic, 
            "--bootstrap-server", "localhost:9092", 
            "--partitions", partition,
            "--replication-factor", replication
        ])
