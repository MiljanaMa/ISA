import json, pika, uuid, threading, os 
from contract import *
from validation import *
from termcolor import colored 

def callback_update(ch, method, properties, body):
    contract = next((c for c in contracts if c.id == int(body)),None)
    contract.status = status_mapping[method.routing_key]
    inbox.append(["[x] Status of contract ID: " +" changed to " + status_mapping[method.routing_key].name, False])
    if not input_mode:
        refresh_display()
    

def add_contract(auto = False):
    global new_id, contracts 
    if not auto: 
        id = None
        date = validate_int('Enter day in month: ')
        time = '12:00'
        equipment = input("Enter equipment: ")
        total = validate_int('Enter total count: ')
        company = input("Enter company: ")
        status = Status.INACTIVE
        hospital = input("Enter hospital: ")
    else: 
        id = None 
        date = '28'
        time = '12:12'
        equipment = 'SafetyKit Plus'
        total = 5 
        company = 'Tech Innovators'
        status = Status.INACTIVE
        hospital = 'Beverly Hospital'


    new_contract = Contract(id, date, time, equipment, total, company, status, hospital)
    send_message(json.dumps(new_contract.to_dict(),indent=1))
    
    channel.basic_consume(callback_queue, on_message_callback=callback, auto_ack= True)
    connection.process_data_events(time_limit=None)

    if(new_id != -1):
        new_contract.id = new_id 
        contracts.append(new_contract)
    
   
 

def update_contract():
    
    contract_id = input("Select contract id to update: ")
    contract = next((c for c in contracts if c.id == int(contract_id)),None)

    if contract:
        if contract.status == Status.INACTIVE: 
            contract.date = validate_int('Enter day of the month:')
            contract.time = '12:00'
            contract.equipment = input("Enter new equpiment: ")
            contract.total = validate_int('Enter the total count: ')
            contract.company = input("Enter new company: ")
            if contract.status == Status.INACTIVE: 
                send_message(json.dumps(contract.to_dict(), indent= 1))
            else: 
                print("ERROR: Contract became inactive in the meantime")
        else: 
            print("ERROR: Contract must be inactive")
    else:
        print("ERROR: Contract not found")

    

def read_contracts():
    
    if not contracts:
        print("No contracts available")
    for c in contracts:
        print(json.dumps(c.to_dict(), indent=1))

def show_inbox():
    if len(inbox) != 0:  
        for message in inbox: 
            color = 'white' if message[1] else 'green'
            print(colored(message[0], color))
            message[1] = True 
    else: 
        print("Inbox is empty")


def count_unread(): 
    return sum(1 for message in inbox if not message[1])
    

def callback(ch, method, properties, body):
    global new_id, corr_id
   
    if properties.correlation_id == corr_id: 
        try: 
           new_id = int(body)
           print(' [*] Received ID:', new_id)
        except Exception as e: 
            new_id = -1 
            print(" [*] Received message: "+body.decode()) 



def send_message(body):
    global corr_id
    corr_id = str(uuid.uuid4())
    properties = pika.BasicProperties(reply_to= callback_queue, correlation_id= corr_id)
    channel.basic_publish(exchange='', routing_key='contract',properties=properties, body = body)
    print('[x] Sent:\n' + body)



new_id = -1 
contracts = [] 
corr_id = None 
input_mode = False 
inbox = []
connection = pika.BlockingConnection(
    pika.ConnectionParameters(host = 'localhost')
)

connection2 = pika.BlockingConnection(
    pika.ConnectionParameters(host = 'localhost')
)
channel = connection.channel()

channel.queue_declare(queue = 'contract')
callback_queue = channel.queue_declare(queue = '', exclusive=True).method.queue 


channel2 = connection2.channel()
update_queue = channel2.queue_declare(queue = 'update_queue', exclusive= True).method.queue

keys = ["finish", "start", "cancel"]
channel2.exchange_declare(exchange = 'direct_updates', exchange_type='direct', durable= True)

for k in keys: 
    channel2.queue_bind(exchange='direct_updates', queue = update_queue, routing_key=k)

channel2.basic_consume(queue=update_queue, on_message_callback= callback_update, auto_ack= True)

def refresh_display():
    os.system('cls' if os.name == 'nt' else 'clear') 
    print("\nPick an operation\
           \n1. Add contract\
           \n2. Update contract\
           \n3. Read contracts\
           \n4. Read INBOX(" + str(count_unread()) + ")\
           \n0. Exit")
    
def main():

  
   

    def listen_for_update():
        channel2.start_consuming()
    
    listener_thread = threading.Thread(target= listen_for_update)
    listener_thread.start()

    add_contract(True)
    add_contract(True)
    add_contract(True)
    
    read_contracts()
    while True:
          refresh_display()

        
        
          
          choice = input("Enter your choice: ")
          global input_mode 
          input_mode = True 
          if choice == '1':
              add_contract()
          elif choice == '2':
              update_contract()
          elif choice == '3':
              read_contracts()
          elif choice == '4': 
              show_inbox()
          elif choice == '0':
              print("Exiting...")
              break
          else:
            print("Invalid choice try again")
          input("Press enter to continue...")
          input_mode = False 
          
    
    connection.close()

if __name__ == '__main__':
    main()
