import json
import pika
import threading
from coordinate import *
import time

coordinates = []
connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost'))

channel = connection.channel()

channel.queue_delete(queue='startsending')
channel.queue_delete(queue='coordinates')
channel.queue_declare(queue='coordinates', durable=True)

#channel.queue_purge(queue='startsending')
#channel.queue_purge(queue='coordinates')

channel.queue_declare(queue='coordinates', durable=True)
channel.queue_declare(queue='startsending', durable=False)

stop_sending = False

def send_message(m):
    channel.basic_publish(exchange='', routing_key='coordinates', body=m)
    print(" [x] Sent:\n " + m)

def start_sending_coordinates():
    while not stop_sending:
        method_frame, header_frame, body = channel.basic_get(queue='startsending')
        if method_frame:
            main()

def main():
    coordinates_data = [
        (45.240025, 19.825688),
        (45.240652, 19.825366),
        (45.240984, 19.825345),
        (45.241393, 19.825333),
        (45.242195, 19.825297),
        (45.242896, 19.825250),
        (45.243714, 19.825221),
        (45.244751, 19.825208),
        (45.245492, 19.825129),
        (45.246334, 19.825103),
        (45.246890, 19.825090),
        (45.247269, 19.825103),
        (45.247343, 19.824727),
        (45.247353, 19.824245),
        (45.247364, 19.823959),
        (45.247247, 19.823937)
    ]

    for i, coord_data in enumerate(coordinates_data, start=1):
        new_coordinate = Coordinate(coord_data[0], coord_data[1])
        send_message(json.dumps(new_coordinate.to_dict(), indent=1))
        coordinates.append(new_coordinate)
        time.sleep(3) 

if __name__ == '__main__':
    try:
        while True:
            method_frame, header_frame, body = channel.basic_get(queue='startsending')
            if method_frame:
                main()
    except KeyboardInterrupt:
        stop_sending = True
        connection.close()

