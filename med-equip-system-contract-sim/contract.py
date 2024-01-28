from enum import Enum 

class Status(Enum): 
    INACTIVE = 1 
    ACTIVE = 2 
    CANCELLED = 3
    FINISHED = 4
    STARTED = 5 

status_mapping = {
    "start": Status.ACTIVE, 
    "finish": Status.FINISHED,
    "cancel": Status.CANCELLED,
    "started": Status.STARTED,
}

class Contract:
    def __init__(self, id, date, time, equipment, total, company, status):
        self.id = id
        self.date = date
        self.time = time
        self.equipment = equipment
        self.total = total
        self.company = company
        self.status = status 

    def to_dict(self):
        return {"id": self.id,
                "date": self.date,
                "time": self.time,
                "equipmentName": self.equipment,
                "total": self.total,
                "companyName": self.company, 
                "status": self.status.name}

